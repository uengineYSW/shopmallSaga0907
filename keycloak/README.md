---

# Getting Started

1. Started keycloak
2. Keycloak Setting
3. Gateway Setting
4. Running Gateway


# 1. Started Keycloak
## Docker
docker run -p 9090:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin quay.io/keycloak/keycloak:15.0.2
 * port-forward 9090->8080 
 * keycloak admin Page id: admin /pw : admin
    
## Local 
#step1. Download Keycloak
- Linux/Unix
```yaml
$ wget https://github.com/keycloak/keycloak/releases/download/15.0.2/keycloak-15.0.2.zip
```
- Windows
```text
https://github.com/keycloak/keycloak/releases/download/15.0.2/keycloak-15.0.2.zip.sha
```
다운로드후 파일 업로드

#step2. Installing
Unpack the ZIP file using the appropriate unzip utility, such as unzip, tar, or Expand-Archive.
```yaml
$ unzip keycloak-15.0.2.zip
or
$ tar -zxvf keycloak-15.0.2.tar.gz
```
#step3. Starting
If you want to make DockerImage, you move Dockerfile to /keycloak-15.0.2 folder.
```yaml
$ cd keycloak-15.0.2/bin
```

- Linux/Unix
```
$ sh standalone.sh -Djboss.socket.binding.port-offset=1010 
    or
$ ./standalone.sh -Djboss.socket.binding.port-offset=1010
```
- Windows
```
$ \bin\standalone.bat -Djboss.socket.binding.port-offset=1010
```

port-offset=0 => port 8080
port-offset=1010 => port 9090

Open http://localhost:9090/auth in your web browser.


# 2. Keycloak Setting
#step1. admin 접속
http://localhost:9090/auth 접속 > admin 계정 생성.

#step2. Clients setting
1. Client create >  client-id

2. Move to Setting Tab
    - Access Type : public ->  confidential 변경 (public으로 줘야 front main.js에서 토큰 가져와짐)
    - Valid Redirect URIs : 'http://localhost:8088' or '*' (gateway 주소)
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

