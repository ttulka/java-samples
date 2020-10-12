### Docker build
```
docker build -t jmeter-demo-app .
```

### Docker run
```
docker run --rm -p 8080:8080 --memory="256m" --cpus="2" jmeter-demo-app
```