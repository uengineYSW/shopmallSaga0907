---

# Getting Started

1. Started keycloak
2. Keycloak Setting
3. Gateway Setting
4. Running Gateway


# 1. Started Keycloak
## Docker
## Admin 계정 생성 및 https 차단 해제 환경변수 설정
docker run -p 9090:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e PROXY_ADDRESS_FORWARDING=true -e KEYCLOAK_LOGLEVEL=debug -e KC_METRICS_ENABLED=true -e KC_HTTP_ENABLED=false -e KC_PROXY=edge jboss/keycloak
 * port-forward 9090->8080 
 * keycloak admin Page id: admin /pw : admin
    
## Local 
#step1. Download Keycloak
- Linux/Unix
```yaml
$ wget https://github.com/keycloak/keycloak/releases/download/17.0.1/keycloak-17.0.1.zip
```
- Windows
```text
https://github.com/keycloak/keycloak/releases/download/17.0.1/keycloak-17.0.1.zip.sha
```
다운로드후 파일 업로드

#step2. Installing
Unpack the ZIP file using the appropriate unzip utility, such as unzip, tar, or Expand-Archive.
```yaml
$ unzip keycloak-17.0.1.zip
or
$ tar -zxvf keycloak-17.0.1.tar.gz
```
#step3. Starting
If you want to make DockerImage, you move Dockerfile to /keycloak-17.0.1 folder.
```yaml
$ cd keycloak-17.0.1/bin
```

- Linux/Unix
```
$ sh kc.sh start-dev --http-port=9090 
    or
  chmod +x kc.sh
$ ./kc.sh start-dev --proxy=edge --http-port=9090
```
- Windows
```
$ \bin\kc.bat start-dev --http-port=9090
```


Open http://localhost:9090/ in your web browser.


# 2. Keycloak Setting
#step1. admin 접속
http://localhost:9090/ 접속 > admin 계정 생성.

#step2. Clients setting
1. Client create >  client-id

2. Move to Setting Tab
    - Access Type : public
    - Root URL : 'http://localhost:9090/*'
    - Valid Redirect URIs : 'http://localhost:8088/*' or '*' (gateway 주소)
    - Web Origins : '*'
    - Service Accounts Enabled : off -> on
4. Clients -> Credentials -> client-secret

#step3. User register
Users > 
1. add User > 정보 입력 > 저장
2. 'view all users' 클릭
3. user id 클릭 > Credentials > password 설정

# 3. Gateway Setting
1. gateway >  application.yml

Insert application.yaml
````yaml
spring:
  security:
    oauth2:
      client:
        provider:
          keycloak:
            issuer-uri: ${keycloak-client.server-url}/realms/${keycloak-client.realm}
            user-name-attribute: preferred_username
        registration:
          keycloak:
            client-id: ${client-id}
            client-secret: ${client-secret}
            redirect-uri: "{baseUrl}/login/oauth2/code/keycloak"
            authorization-grant-type: authorization_code
            scope: openid
      resourceserver:
        jwt:
          jwk-set-uri: ${keycloak-client.server-url}/realms/${keycloak-client.realm}/protocol/openid-connect/certs
````
# 4. Running Gateway
    
    
    
### Keycloak yaml
1. keycloak-service.yaml
```yaml
apiVersion: v1
kind: Service
metadata:
  name: keycloak
  labels:
    app: keycloak
spec:
  ports:
  - name: http
    port: 8080
    targetPort: 8080
  selector:
    app: keycloak
  type: LoadBalancer
```

2. keycloak-deployment.yaml
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: keycloak
  namespace: default
  labels:
    app: keycloak
spec:
  replicas: 1
  selector:
    matchLabels:
      app: keycloak
  template:
    metadata:
      labels:
        app: keycloak
    spec:
      containers:
      - name: keycloak
        image: quay.io/keycloak/keycloak:15.0.2
        env:
        - name: KEYCLOAK_USER
          value: "admin"
        - name: KEYCLOAK_PASSWORD
          value: "admin"
        - name: PROXY_ADDRESS_FORWARDING
          value: "true"
        ports:
        - name: http
          containerPort: 8080
        - name: https
          containerPort: 8443
        readinessProbe:
          httpGet:
            path: /auth/realms/master
            port: 8080
```
            
#Documentation
https://www.keycloak.org/docs/latest/getting_started/index.html

#vue.js
https://www.keycloak.org/securing-apps/vue

