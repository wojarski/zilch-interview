## The Laundry
1. run `Application`
2. open `localhost:8080/h2-console` put JDBC URL `jdbc:h2:mem:test` user `sa/password` to browse DB
3. HTTP GET `http://localhost:8080/quickLaundry` to create and save a new Laundry
4. observe `LAUNDRY_ENTITY` and `DEVICE_OUTBOX_ENTITY` tables