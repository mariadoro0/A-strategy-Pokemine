
# Pokémon Card API Documentation

This API allows users to manage a collection of Pokémon cards, create decks, and authenticate as users. With the functionality to search for cards, manage user data, and validate decks, this API provides a robust foundation for Pokémon card collection and organization.

## Detailed Descriptions of API Endpoints
- [Cards](#cards)
- [User Management](#user-management)
- [Collection Management](#collection-management)
- [Deck Management](#deck-management)
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
- #### /users/signin
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



- ### user/deactivate: 
    Method: `GET`

    Request parameter required: `userId`

    Temporarily deactivate a user by their ID: sets the isActive boolean property from 1 to 0.

    Example:
    ```request
    localhost:8080/users/deactivate?userId=1
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
    Method: `GET`

    Request parameter required: `userId`

    Definetly delete a user by their ID: also deletes the user's card collection and decks.

    Example:
    ```request
    localhost:8080/users/deleteaccount?userId=1
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
    
- #### /collection/{userId}
    Method: `GET`

     Fetch all cards in a user’s collection using `userid`.

    A request example will be:
    ```request
    localhost:8080/collection/1
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



- #### /collection/{userid}/add?cardId={cardId}
    Method: `POST`

     Add a new card to a user’s collection by specifying `cardid`.
     Example:
    ```request
    localhost:8080/collection/1/add?cardId=base1-1
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
- #### /collection/{userid}/remove?cardId={cardId}
    Method: `POST`

     Removes a card from a user’s collection by specifying `cardid`.
     Example:
    ```request
    localhost:8080/collection/1/remove?cardId=base1-1
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
- ### /decks/{userId}
    Method: `GET`

     Fetch all user's decks using `userid`.

    A request example will be:
    ```request
    localhost:8080/decks/1
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

- ### /decks/{userId}/newdeck
    Method: `POST`

        Creates a new deck for the user using `userid`, specifying the name and the description of the deck as request parameters.

    A request example will be:
    ```request
    localhost:8080/decks/1/newdeck?name=newdeck&description=new description
    ```
    A <span style="color:green">successful</span> operation will produce an output like:
    ```
    New deck created successfully.
    ```

- ### /decks/{userId}/{deckId}
    Method: `GET`

     Fetch all cards in the deck by using `userid` and `deckId`. 
     The response will show a map with the card id and relative quantity.

    A request example will be:
    ```request
    localhost:8080/decks/1/2
    ```
    And the response will look like:
    ```json
    {
        "base1-1": 1
    }
    ```

- ### /decks/{userId}/{deckId}/validate
    Method: `GET`

     Performs the deck validation by using `userid` and `deckId`. 
     The deck will result valid if it has 60 cards and at least one Basic Pokémon.

    A request example will be:
    ```request
    localhost:8080/decks/1/2/validate
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

- ### /decks/{userid}/{deckId}/add?cardId={cardId}
    Method: `POST`

     Add a new card to a user’s deck by specifying `cardid`.

     **Note**: the user can add to his decks only cards he owns in his collection.

     Example:
    ```request
    localhost:8080/decks/1/2/add?cardId=base1-1
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

- ### /decks/{userid}/{deckId}/remove?cardId={cardId}
    Method: `POST`

     Remove a card from a user’s deck by specifying `cardid`.

    

     Example:
    ```request
    localhost:8080/decks/1/2/remove?cardId=base1-1
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

- ### /decks/{userId}/{deckId}/deletedeck
    Method: `GET`

     Deletes the selected deck.

    A request example will be:
    ```request
    localhost:8080/decks/1/2/delete
    ```
    And the <span style="color:green">successful</span> response will look like:
    ```
    Deck deleted.
    ```


---

## Authentication and Authorization

To manage user-specific collections and decks, this API requires proper user authentication. Ensure each request includes valid authentication tokens where necessary.

## API Overview

| Endpoint                                         | Method   | Description                                | Parameters                                   | Request Body |
|--------------------------------------------------|----------|--------------------------------------------|----------------------------------------------|--------------|
| `localhost:8080/cards`                           | GET      | Fetch all cards                            | `name`, `type`, `artist`, `set`, `rarity`,<br>`supertype`, `series`, `generation`, `id`, `page` |            
| `localhost:8080/users/signin`                     | POST     | Sign in a new user                         | `username`, `email`, `password`              | yes          |
| `localhost:8080/users/deleteaccount`              | GET      | Remove user by ID                          | `id`                                         |           |
| `localhost:8080/users/deactivate`                 | GET      | Deactivate user by ID                      | `id`                                         |           |
| `localhost:8080/collection/`                      | GET      | Fetch all cards in a user's collection     | `userid`                                     |           |
| `localhost:8080/collection/{userid}/add`          | POST     | Add a new card to user’s collection        | `cardid`                                     |           |
| `localhost:8080/collection/{userid}/remove`       | POST     | Remove a card from user’s collection       | `cardid`                                     |           |
| `localhost:8080/decks/{userid}`                   | GET      | Fetch all decks by user ID                 | -                                            |            |
| `localhost:8080/decks/{userid}/newdeck`           | POST     | Add a new deck to user                     | `name`, `description`                        |           |
| `localhost:8080/decks/{userid}/{deckid}`          | GET      | Fetch deck by deck ID for a specific user  | -                                            |            |
| `localhost:8080/decks/{userid}/{deckid}/validate` | GET      | Validate deck                              | -                                            |            |
| `localhost:8080/decks/{userid}/{deckid}/add`      | POST     | Add a card to deck                         | `cardid`                                     |           |
| `localhost:8080/decks/{userid}/{deckid}/remove`   | POST     | Remove a card from deck                    | `cardid`                                     |           |
| `localhost:8080/decks/{userid}/{deckid}/deletedeck` | GET   | Remove a deck by deck ID                   | `deckid`                                     |           |

---
