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

The project is configured to connect to a MariaDB/MySQL instance with the URL `jdbc:mariadb://localhost:3306/pokemine`. Ensure credentials are correctly set up in the `application.properties` file.

Example configuration (`application.properties`):

```properties
spring.application.name=Pokemine
spring.datasource.url=jdbc:mariadb://localhost:3306/pokemine
spring.datasource.username=root
spring.datasource.password=
```

### API TEST  Table 
#### some examples of API calls
| Endpoint                                         | Method   | Description                                | Parameters                                   | request body |
|--------------------------------------------------|----------|--------------------------------------------|----------------------------------------------|---------|
| `localhost:8080/cards/`                           | GET      | Fetch all Cards                            | `name`,`type`,`artist`,`set`,`rarity`,<br>`supertype`,`series`,`generation`,`id`,`page` | - |
| `localhost:8080/cards/page`                    | GET        | Fetch  First 100 Cards                           | `page`                | yes |
| `localhost:8080/users/signin`                    | POST        | Add new user                          | `username`,`email`,`password`                | yes |
| `localhost:8080/users/deleteaccount`              | GET         | remove user with id                   | `id`                                         | yes |
| `localhost:8080/users/deactivate`              | GET         | deactivates user with id                   | `id`                                         | yes |
| `localhost:8080/collection/` | GET         | Fetch all Cards of collection         | `userid`                                         | yes
| `localhost:8080/collection/{userid}/add`  | POST        | Add new Card to user collection            |  `cardid`                           | yes
| `localhost:8080/collection/{userid}/remove`| POST       | remove Card From collection           | `cardid`                            | yes
| `localhost:8080/decks/{userid}` | GET         | Fetch Deck by User id                 | `None`                                     | - |
| `localhost:8080/decks/{userid}/newdeck`| POST        | Add new Deck to user                  | `name`, `description`                    | yes
| `localhost:8080/decks/{userid}/{deckid}`| GET        | Fecth User with speci. Deck Id        |`None`                                        | - |
| `localhost:8080/decks/{userid}/{deckid}/validate`| GET | validation of decks                 | `None`                                       | - |
| `localhost:8080/decks/{userid}/{deckid}/add`| POST | Add new Card to Deck                 | `cardid`                                        | yes |
| `localhost:8080/decks/{userid}/{deckid}/remove`| POST | remove Card from Deck                 | `cardid`                                        | yes |
| `localhost:8080/decks/{userid}/{deckid}/deletedeck`| GET | remove Deck for user                  | `deckid`                                        | yes |