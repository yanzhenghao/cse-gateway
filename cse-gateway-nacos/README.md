# cse-gateway-nacos

## _框架_

### nacos-springcloud-at
- `auth`: nacos springcloud 自定义鉴权服务
- `service`: nacos springcloud 受保护业务

部署好后，使用service服务暴露的API进行验证：
- _`{endpoint}:8082/hello`_

- `query参数： times   请求次数，可选，默认=10`

- `header参数：可选，可传入任何自定义header，header只为了验证流量特征路由`

## _Nacos专享版场景_
直接接入Nacos专享版引擎，使用`nacos-spring-cloud-at`
