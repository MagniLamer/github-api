# github-api
To test the application clone the project and run the application.
To get the some user reposetories proceed the post request to the endpoint(/public/api/repo) with the body ("userName":"$gitHubLogin") in JSON format.
You can use next curl in Postman.

```bash
curl --location 'localhost:8080/public/api/repo' \
--header 'Content-Type: application/json' \
--data '{
    "userName":"MagniLamer"
}'
```
You will get the next response:

```json
[
    {
        "name": "backend-springboot",
        "owner": {
            "login": "MagniLamer"
        },
        "branchesAndCommits": [
            {
                "name": "developer",
                "commit": {
                    "sha": "51eae76a0b30e189b3966b31aa343f5c879c3077"
                }
            },
            {
                "name": "master",
                "commit": {
                    "sha": "4ebf724b284b49da5d1e6f046f9e9ffa1dd7c146"
                }
            }
        ]
    },
    {
        "name": "Collections",
        "owner": {
            "login": "MagniLamer"
        },
        "branchesAndCommits": [
            {
                "name": "master",
                "commit": {
                    "sha": "f485a01914f45549bc0f1f3a2f8edde5ac7ce659"
                }
            }
        ]
    },
    {
        "name": "ConcurencyRace",
        "owner": {
            "login": "MagniLamer"
        },
        "branchesAndCommits": [
            {
                "name": "master",
                "commit": {
                    "sha": "b74248bbb545f07935911f7fb7e4f755ccc8efa5"
                }
            }
        ]
    },
    {
        "name": "github-api",
        "owner": {
            "login": "MagniLamer"
        },
        "branchesAndCommits": [
            {
                "name": "master",
                "commit": {
                    "sha": "eba19c825ae8d780fee0e0b26885cf95a63dc280"
                }
            }
        ]
    },
    {
        "name": "test-task",
        "owner": {
            "login": "MagniLamer"
        },
        "branchesAndCommits": [
            {
                "name": "main",
                "commit": {
                    "sha": "19c340994dd2b47536624b893d88beb92625d6c8"
                }
            }
        ]
    }
]
```

If you use the incorrect loggin in the request
```bash
curl --location 'localhost:8080/public/api/repo' \
--header 'Content-Type: application/json' \
--data '{
    "userName":"MagniLame"
}'
```

you will get the next response:

```json
{
    "message": "Repository with name MagniLame does not exist",
    "status": "404 NOT_FOUND"
}
```

If you use the incorrect type in the request:
```bash
curl --location 'localhost:8080/public/api/repo' \
--header 'Content-Type: application/xml' \
--data '{
    "userName":"MagniLame"
}'
```
you will get the next response:
```json
{
    "message": "Please use a correct content type.",
    "status": "406 NOT_ACCEPTABLE"
}
```
