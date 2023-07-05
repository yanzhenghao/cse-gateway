- Service
  - GET
    - /hello 
    - /service?conditions=1
  - POST
    - /service
    - ```json
      {
          "valueMap":{
              "test1":"test1",
              "test2":"test2"
          }
      }
      ```
  - PATCH
      - /service
    ```json
      {
          "valueMap": {
              "test1": "test111"
          }
      }
      ```
  - PUT
      - /service
    ```json
      {
          "valueMap": {
              "test3": "test3"
          }
      }
      ```
  - DELETE
      - /service
    ```json
      {
          "valueMap": {
              "test3": "test3"
          }
      }
      ```