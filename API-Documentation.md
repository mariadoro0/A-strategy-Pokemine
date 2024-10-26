
# Pokémon Card API Documentation

This API allows users to manage a collection of Pokémon cards, create decks, and authenticate as users. With the functionality to search for cards, manage user data, and validate decks, this API provides a robust foundation for Pokémon card collection and organization.

## Detailed Descriptions of API Endpoints
- [Cards](#cards)
- [User Management](#user-management)
- [Collection Management](#collection-management)
- [Deck Management](#deck-management)
- [Authentication and Authorization](#authentication-and-authorization)
- [Api Overview](#api-overview)

 ## Cards
- ### /cards
    *Method*: `GET`
    
    Displays all cards: can receive multiple request parameters to filter the general card database.
    Here is an overview with relative examples:
    | Endpoint examples                                          | Description                                | Parameters                                   | Parameter type |
    |--------------------------------------------------|--------------------------------------------|----------------------------------------------|--------------|
    | `localhost:8080/cards`                           | Fetch all cards                            | `none`             |
    | `localhost:8080/cards?page=2`                    | Pagination system. Each page contains 100 cards.                  | `page`                                       | `int`          |
    | `localhost:8080/cards?id=base1-1`                    | Retrieves a specific card by its id.                     | `id`              | `String`          |
    | `localhost:8080/cards?name=Charizard`              | Retrieves all cards of the specified Pokémon.  | `name` | `String`         |
    | `localhost:8080/cards?series=XY`                 | Retrieves all cards from the specified series.    | `series`  | `String`          |
    | `localhost:8080/cards?artist=Ken Sugimori`                      | Retrieve all cards by the specified artist.     | `artist` | `String`          |
    | `localhost:8080/cards?type=Grass`                      | Retrieve all cards with the specified type.     | `type` | `String`          |
    | `localhost:8080/cards?set=Team Rocket`                      | Retrieve all cards from a specific set.     | `set` | `String`          |
    | `localhost:8080/cards?generation=First`                      | Retrieve all cards from the specified generation.     | `generation` | `String`          |  
    | `localhost:8080/cards?rarity=Rare Shiny`                      | Retrieve all cards with the specified rarity.     | `rarity` | `String`          |
    | `localhost:8080/cards?supertype=Trainer`                      | Retrieve all cards with the specified supertype.     | `supertype` | `String`          |     
    
  All parameters can be combined to filter cards as desired.
    For example, the request could be:

    ```request
    localhost:8080/cards?name=Bulbasaur&series=Base
    ```

    And the response obtained would be:
    ```json
    {
    "cards": [
        {
            "id": "base1-44",
            "setName": "Base",
            "series": "Base",
            "publisher": "WOTC",
            "generation": "First",
            "releasedate": "1/9/1999",
            "artist": "Mitsuhiro Arita",
           
             //all card info ...

        },
        {
            "id": "base4-67",
            "setName": "Base Set 2",
            "series": "Base",
            "publisher": "WOTC",
            "generation": "First",
            "releasedate": "2/24/2000",
            "artist": "Mitsuhiro Arita",
            "name": "Bulbasaur",
            "setNum": "67",
            "supertype": "Pokémon",
            "cardLevel": "13",
            "hp": 40,
            
                 //all card info ...
        }
    ],
    "currentPage": 1,
    "totPages": 1,
    "totalCards": 2
  }


The response also shows the current page we are on, the total number of result pages and the total items found for the research (in this example, 2).


## User Management
- #### /users/signup
    Method: `POST`

    Register a new user using a unique username, email, and password.
    For example:
    ```request
    localhost:8080/users/signin
    ```
    Request body:
    ```json
    {
        "username":"test",
        "email":"test@example.com",
        "password":"testPassword123"
    }
    ```
    If the operation in <span style="color:green">successful</span>, you will obtain this response:
    ```response
    User created successfully
    ```
    Otherwise, if an <span style="color:red">exception</span> occurs, you will receive one of these possible warnings:
    ```response
    Error creating user: A user already exists with this username.
    ```
    ```response
    Error creating user: A user already exists with this email.
    ```
- #### /users/login
    Method: `POST`

    .
    For example:
    ```request
    localhost:8080/users/login
    ```
    Request body:
    ```json
    {
        "email":"test@example.com",
        "password":"testPassword123"
    }
    ```
    If the operation in <span style="color:green">successful</span>, you will obtain a Bearer token that will identify your user:
    ```json
    {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZWZpbml0aXZvIiwiaWF0IjoxNzI5OTQxNDUzLCJleHAiOjE3Mjk5Nzc0NTN9.Y5u_1AlVc6JkViY0tFyhElPLevMShdvc5Fj9VcS2kEI"}
    ```
    For your future requests, you will need to insert this token into the `Authorization > Bearer token` slot in Postman.


> **_WARNING:_**  The following operations will require for the user to be logged in. You cannot access your collection, you decks and manage your account without being logged in.


- ### user/deactivate: 
    Method: `GET`


    Temporarily deactivate an authetincated user account: sets the isActive boolean property from 1 to 0.

    Example:
    ```request
    localhost:8080/users/deactivate
    ```

    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    Account deactivated successfully.
    ```

    Otherwise, if an <span style="color:red">exception</span> occurs, you will see the following message:
    ```
    Error: ID not present - The user does not exist and cannot be deactivated.
    ```

- #### user/deleteaccount: 
    Method: `DELETE`

    Request parameter required: `userId`

    Definetly delete the authenticated user's account: also deletes the user's card collection and decks.

    Example:
    ```request
    localhost:8080/users/deleteaccount
    ```

    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    Account deleted successfully.
    ```

    Otherwise, if an <span style="color:red">exception</span> occurs, you will see the following message:
    ```
    Error: ID not present - The user does not exist and cannot be deactivated.
    ```


## Collection Management
    
- #### /collection
    Method: `GET`

     Fetch all cards in the user’s collection.

    A request example will be:
    ```request
    localhost:8080/collection
    ```
    And the response will look like:
    ```json
    [
        {
            "id": {
                "userId": 10,
                "cardId": "base1-1"
            },
            "quantity": 1
        },
        {
            "id": {
                "userId": 10,
                "cardId": "base1-100"
            },
            "quantity": 3
        }
  ]
  ```



- #### /collection/add?cardId={cardId}
    Method: `POST`

     Add a new card to the user’s collection by specifying `cardid`.
     Example:
    ```request
    localhost:8080/collection/add?cardId=base1-1
    ```

    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    Card added to the collection
    ```

    Otherwise, if an <span style="color:red">exception</span> occurs, you will see one of the following messages:
    ```
    User with id: 1 not found.
    ```
    ```
    Card with id: base1-1 not found.
    ```
- #### /collection/remove?cardId={cardId}
    Method: `POST`

     Removes a card from the user’s collection by specifying `cardid`.
     Example:
    ```request
    localhost:8080/collection/remove?cardId=base1-1
    ```

    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    Card removed from the collection
    ```

    Otherwise, if an <span style="color:red">exception</span> occurs, you will see one of the following messages:
    ```
    User with id: 1 not found.
    ```
    ```
    Card with id: base1-1 not found.
    ```
    ```
    The card does not exist in the collection.
    ```


### Deck Management
- ### /decks
    Method: `GET`

     Fetch all user's decks.

    A request example will be:
    ```request
    localhost:8080/decks
    ```
    And the response will look like:
    ```json
    [
        {
            "id": 2,
            "deckName": "Deck test",
            "deckDescription": "This is a deck for testing purposes."
        }
    ]
  ```

- ### /decks/newdeck
    Method: `POST`

    Creates a new deck for the user using `userid`, specifying the name and the description of the deck as request parameters.

    A request example will be:
    ```request
    localhost:8080/decks/newdeck?name=newdeck&description=new description
    ```
    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    New deck created successfully.
    ```

- ### /decks/{deckId}
    Method: `GET`

     Fetch all cards in the user's deck by `deckId`. 
     The response will show a map containing the card id and relative quantity.

    A request example will be:
    ```request
    localhost:8080/decks/2
    ```
    And the response will look like:
    ```json
    {
        "base1-1": 1
    }
    ```

- ### /decks/{deckId}/validate
    Method: `GET`

     Performs the deck validation by using  `deckId`. 
     The deck will result valid if it has 60 cards and at least one Basic Pokémon.

    A request example will be:
    ```request
    localhost:8080/decks/2/validate
    ```
    A <span style="color:green">successful</span> validation will look like:
    ```response
    Deck is valid and ready to play!
    ```
    Otherwise, if the deck is <span style="color:red">not valid</span>, a message will explain to you why:
    ```response
    You don't have enough cards.
    Card of type Pokemon Basic not found.
    ```

- ### /decks/{deckId}/add?cardId={cardId}
    Method: `POST`

     Add a new card to a user’s deck by specifying `cardid`.

     **Note**: the user can add to his decks only cards he owns in his collection.

     Example:
    ```request
    localhost:8080/decks/2/add?cardId=base1-1
    ```

    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    Card added to the deck
    ```

    Otherwise, if an <span style="color:red">exception</span> occurs, you will see one of the following messages:
    ```
    User with id: 1 not found.
    ```
    ```
    Card with id: base1-1 not found.
    ```
    ```
    Deck with id: 2 not found.
    ```
    ```
    The deck does not belong to the selected user
    ```
    ```
    The card is not present in the user's collection.
    ```
    ```
    You cannot add more than 4 copies of the same non-Basic Energy card.
    ```
    ```
    You do not own enough copies of this card in your collection.
    ```

- ### /decks/{deckId}/remove?cardId={cardId}
    Method: `POST`

     Remove a card from a user’s deck by specifying `cardid`.

    

     Example:
    ```request
    localhost:8080/decks/2/remove?cardId=base1-1
    ```

    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    Card removed from the deck
    ```

    Otherwise, if an <span style="color:red">exception</span> occurs, you will see one of the following messages:
    ```
    User with id: 1 not found.
    ```
    ```
    Card with id: base1-1 not found.
    ```
    ```
    Deck with id: 2 not found.
    ```
    ```
    The deck does not belong to the selected user
    ```

- ### /decks/{deckId}/deletedeck
    Method: `GET`

     Deletes the selected deck.

    A request example will be:
    ```request
    localhost:8080/decks/2/delete
    ```
    And the <span style="color:green">successful</span> response will look like:
    ```
    Deck deleted.
    ```


---

## Authentication and Authorization

To manage user-specific collections and decks, this API requires proper user authentication by using Bearer tokens, which are generated at the login moment. 
Ensure each request includes the valid authentication token where necessary.

## API Overview

| Endpoint                                         | Method   | Description                                | Parameters                                   | Request Body | Auth needed |
|--------------------------------------------------|----------|--------------------------------------------|----------------------------------------------|--------------|----|
| `localhost:8080/cards`                           | GET      | Fetch all cards                            | `name`, `type`, `artist`, `set`, `rarity`,<br>`supertype`, `series`, `generation`, `id`, `page` |       |    no | 
| `localhost:8080/users/signin`                     | POST     | Sign in a new user                         | `username`, `email`, `password` | yes | no|
| `localhost:8080/users/login`                     | POST     | Logs in the user and gives an auth token                         |  `email`, `password`              | yes          | no: will create the token |
| `localhost:8080/users/deleteaccount`              | DELETE      | Remove logged user                         | `id`                                         |           | yes |
| `localhost:8080/users/deactivate`                 | GET      | Deactivate logged user                      | `id`                                         |           | yes|
| `localhost:8080/collection`                      | GET      | Fetch all cards in a user's collection     | `userid`                                     |           | yes |
| `localhost:8080/collection/add`          | POST     | Add a new card to user’s collection        | `cardid`                                     |           | yes |
| `localhost:8080/collection/remove`       | POST     | Remove a card from user’s collection       | `cardid`                                     |           | yes |
| `localhost:8080/decks`                   | GET      | Fetch all auth user's decks                  | -                                            |            | yes |
| `localhost:8080/decks/newdeck`           | POST     | Add a new deck to auth user's decks                     | `name`, `description`                        |           | yes |
| `localhost:8080/decks/{deckid}`          | GET      | Fetch deck by deck ID for a specific user  | -                                            |            | yes |
| `localhost:8080/decks/{deckid}/validate` | GET      | Validate deck                              | -                                            |            | yes |
| `localhost:8080/decks/{deckid}/add`      | POST     | Add a card to deck                         | `cardid`                                     |           | yes |
| `localhost:8080/decks/{deckid}/remove`   | POST     | Remove a card from deck                    | `cardid`                                     |           |  yes |
| `localhost:8080/decks/{deckid}/deletedeck` | GET   | Remove a deck by deck ID                   | `deckid`                                     |           | yes |

---
