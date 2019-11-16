spring:
  application:
    name: zuul
  cloud:
    config:
      discovery:
        enabled: true
        service-id: SPRINGCLOUDCONFIG   #springcloud在eureka里的名称
      profile: dev  #选择环境配置

eureka:
  instance:
    prefer-ip-address: true
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:10000/eureka/

# 必须加此配置，否则zuul的http://localhost:5555/actuator/routes无法访问（查看所有路由）
management:
  endpoints:
    web:
      exposure:
        include: '*'

#在zuul的url增加api的prefix，如http://192.168.31.139:5555/api/demo/demo
zuul:
  prefix: /api

# ribbon超时不能配置在springbootcloud的config中，需要配置在本地