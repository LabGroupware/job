# 手動Build手順

``` sh
./gradlew bootBuildImage --imageName=ablankz/nova-job-service:1.0.3
docker push ablankz/nova-job-service:1.0.3
```