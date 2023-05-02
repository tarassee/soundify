package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('Get an audio file by ID')

    request {
        url '/resources/1'
        method 'GET'
        headers {
            accept applicationOctetStream()
        }
    }

    response {
        status 200
        headers {
            contentType applicationOctetStream()
        }
        body('e\u0015 A')
    }
}
