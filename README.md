# Pokémine: a Pokémon Java API

Pokémine is a Java-based API for managing Pokémon cards, user card collections, and card decks. The application supports user creation, managing their collections and decks, and advanced card search capabilities with filters.

## Key Features

- **Card Search**: Enables searching for Pokémon cards using various filters, such as name, type, subtype, and specific characteristics.
- **User Management**: Supports creating new users and managing their personal information.
- **User Card Collection**: Allows saving and managing each user's card collection.
- **User Decks**: Supports creating and managing personal card decks for each user.

## Prerequisites

- **Java**: Requires JDK version 17 or later.
- **XAMPP with MariaDB**: The database used is MariaDB, configured for connection via JDBC.
- **Maven**: This project uses Maven for dependency management.
- **API client**: Any software that allows you to do an API call.

## Database Configuration

The project is configured to connect to a MariaDB/MySQL instance with the URL `jdbc:mariadb://localhost:3306/pokemine`. 

Ensure credentials are correctly set up in the `application.properties` file.

Example configuration (`application.properties`):

```properties
spring.application.name=Pokemine
spring.datasource.url=jdbc:mariadb://localhost:3306/pokemine
spring.datasource.username=root
spring.datasource.password=
```

**_Further info_**: For a more detailed guide on how to setup your database, see the [database documentation](./Database-Documentation.md) 

### API TEST  Table 
**_Further info_**: For a more detailed guide on how the API calls work, see the[ API documentation](./API-Documentation.md)
#### some examples of API calls
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

### Frontend
We provided a Frontend folder with mockup static pages displaying how our API can collaborate with a front-end client.
See [Frontend documentation](./manuale_utente/README.md) to see more.