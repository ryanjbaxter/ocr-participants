package contracts.participants;

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/'
        headers {
        }
    }
    response {
        status 200
        body("""
                [{"firstName":"Ryan","lastName":"Baxter","homeState":"MA","shirtSize":"S","races":["123","456"]},
                {"firstName":"Stephanie","lastName":"Baxter","homeState":"MA","shirtSize":"S","races":["456"]}]
             """)
        headers {
            contentType('application/json')
        }
    }
}