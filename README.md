# Job Management

### Only Setup
``` sh
sudo chmod +x ./init.sh ./down.sh 
```

起動
``` sh
./init.sh
```

停止
``` sh
./down.sh
```

### Grpc

``` sh
grpcurl --plaintext -d '{}' localhost:9100 job.v1.JobService/CreateJob
```

### Prerequire
- [asdf](./setup_asdf.md)

### Setup
#### コマンドセットアップ
``` sh
asdf plugin add grpcurl
asdf plugin add kafka
asdf install
```
