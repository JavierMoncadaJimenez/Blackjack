# Blackjack
Is the game of blackjack. This is a project for the university.

This proyect consist in a server to control all clientele and all lobbyes. The server allows only two players in the lobby and when all the lobbies are full, the server creates new lobbies for the new players to play a game.

When two players join the lobby, the game starts. When it's a player's turn, that player must choose between taking a card, not getting any more carts, or leaving the lobby.
If the player takes a new cart, the server will give a new cart and check that the player's cards don't exceed 21 points. If the player has less than 21 points, the server will only change the turn to the other player, but if the player has more than 21 points, the server will play the game with the croupier and the other player.

If a player doesn't want any cards, the server won't deal that player any more cards. If all players don't want any more cards, the server will deal cards to the dealer and it will stop when the dealer has more than 16 points.

For the last part, the server checks which player has less points to 21 and is closest to 21 points to give the win.

When the mach finish, the server start other mach for the players.
