package az.azercellfintech.otp.ms.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static lombok.AccessLevel.PRIVATE;

@Log
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RedisUtil {

    RedissonClient redissonClient;

    public <T> void save(String key, T value, Long timeToLive) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, timeToLive, SECONDS);
    }

    public <T> void updateOrDelete(String key, T value, Long timeToLive) {
        if (timeToLive <= 0) {
            removeKey(key);
        } else {
            RBucket<T> bucket = redissonClient.getBucket(key);
            bucket.set(value, timeToLive, SECONDS);
        }
    }

    public <T> Optional<T> get(String key, Class<T> responseType) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return nonNull(bucket) ? ofNullable(bucket.get()) : empty();
    }

    public void removeKey(String key) {
        var bucket = redissonClient.getBucket(key);
        if (nonNull(bucket)) bucket.delete();
    }
}
