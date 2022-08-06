# Pack Item
Pack Item is a restful ready for use api to group item in box with weight limitation.
The api group item with size sames with weight.

```shell
curl --location --request POST 'localhost:8080/boxes' \
--header 'Content-Type: application/json' \
--data-raw '{
  "max": 2,
  "items": [
    {
      "name": "product name 1",
      "weight": 1.9
    },
    {
      "name": "product name 2",
      "weight": 0.2
    },
    {
      "name": "product name 3",
      "weight": 1.7
    },
    {
      "name": "product name 4",
      "weight": 0.1
    }
  ]
}
'
```

## Input

```json
{
  "max": 2,
  "items": [
    {
      "name": "product name 1",
      "weight": 1.9
    },
    {
      "name": "product name 2",
      "weight": 0.2
    },
    {
      "name": "product name 3",
      "weight": 1.7
    },
    {
      "name": "product name 4",
      "weight": 0.1
    }
  ]
}
```
## Output
```json
{
  "total": 3.90,
  "boxes": [
    {
      "weight": 2.00,
      "items": [
        "product name 1",
        "product name 4"
      ]
    },
    {
      "weight": 1.90,
      "items": [
        "product name 3",
        "product name 2"
      ]
    }
  ]
}
```


# Docker
```shell
docker run --rm -d -p 8080:8080 --name test-pack-it xethhung/pack-item
```

# Installation
```shell
mvn clean compile package intall
```





