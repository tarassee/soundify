package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('Upload a song')

    request {
        url '/songs'
        method 'POST'
        headers {
            contentType applicationJson()
        }
        body("""
            {
                "name": "We are the champions",
                "artist": "Queen",
                "album": "News of the world",
                "length": "2:59",
                "resourceId": 12,
                "day": "1977"
            }
        """)
    }

    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body("""
            {
                "id": 1
            }
        """)
    }
}
