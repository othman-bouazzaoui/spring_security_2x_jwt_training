## Spring Security : stateful and Stateless approaches

To authenticate :
POST http://localhost:9090/login username=Admin&password=123

response will contain access_token & refresh_token
>>>>>>> 810e444 (stateless approache)
### authentication using Postman :
<img src="data/loginFromPostMan.png"/>

### authentication using Front APP :
<img src="data/loginFromFrontEnd.png" />  

### renew the token
http://localhost:9090/api/v1/tokens/new-access-token
<img src="data/renewToken.png" /> 

### Postman Collection
` src/main/resources/Test Security.postman_collection.json `