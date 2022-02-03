package de.hypercdn.simplejwt.imp;

import de.hypercdn.simplejwt.api.JwtEntity;
import de.hypercdn.simplejwt.api.JwtManager;
import de.hypercdn.simplejwt.api.builder.JwtEntityBuilder;
import de.hypercdn.simplejwt.imp.exception.JwtException;
import de.hypercdn.simplejwt.utils.Pair;
import io.jsonwebtoken.Jwts;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static de.hypercdn.simplejwt.api.components.Claims.Common.ISSUER;
import static de.hypercdn.simplejwt.api.components.Claims.Common.JWT_ID;

/**
 * Generic implementation of a jwt manager
 */
public class GenericJwtManager implements JwtManager{

	private final String issuerId;
	private final byte[] globalSigningBytes;
	private final Function<String, byte[]> jwtSigningBytesProvider;
	private final ConcurrentHashMap<String, Pair<byte[], Instant>> jwtSigningByteStore = new ConcurrentHashMap<>();


	public GenericJwtManager(String issuerId, byte[] globalSigningBytes, Function<String, byte[]> jwtSigningBytesProvider){
		this.issuerId = issuerId;
		this.globalSigningBytes = globalSigningBytes;
		this.jwtSigningBytesProvider = jwtSigningBytesProvider;
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> {
			try {
				for(var entry : jwtSigningByteStore.entrySet()){
					if(Instant.now().isAfter(entry.getValue().b())){
						jwtSigningByteStore.remove(entry.getKey());
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}, 1, 1, TimeUnit.MINUTES);
	}

	@Override
	public JwtEntityBuilder newJwt(){
		return new GenericJwtEntityBuilder(this);
	}

	@Override
	public String getIssuerId(){
		return issuerId;
	}

	@Override
	public boolean verify(JwtEntity jwtEntity){
		Objects.requireNonNull(jwtEntity);
		String jwtId = jwtEntity.getClaims().getValueFor(JWT_ID);
		if(jwtId == null){
			throw new JwtException("No identification present on jwt");
		}
		String iss = jwtEntity.getClaims().getValueFor(ISSUER);
		var signingKey = getCombinedSigningBytes(jwtId);
		if(!getIssuerId().equals(iss) || signingKey == null){
			return false;
		}
		try {
			Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parse(jwtEntity.asJwtString());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean invalidate(String jti){
		return jwtSigningByteStore.remove(jti) != null;
	}

	@Override
	public void invalidateAll(){
		jwtSigningByteStore.clear();
	}

	@Override
	public byte[] getGlobalSigningBytes(){
		return globalSigningBytes;
	}

	@Override
	public byte[] getJwtSigningBytes(String jti){
		Objects.requireNonNull(jti);
		var entry = jwtSigningByteStore.get(jti);
		if(entry == null){
			return null;
		}
		return entry.a();
	}

	@Override
	public byte[] receiveJwtSigningBytes(String jti){
		Objects.requireNonNull(jti);
		return jwtSigningBytesProvider.apply(jti);
	}

	@Override
	public void setSigningBytesFor(String jti, byte[] bytes, Duration duration){
		jwtSigningByteStore.put(jti, new Pair<>(bytes, Instant.now().plus(duration)));
	}
}
