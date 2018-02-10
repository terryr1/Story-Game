using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameModel : IGameModel {

    List<IEnemy> currentEnemies;
    List<IPlayer> currentPlayers;
    List<Platform> platforms;

    public GameModel() {
        currentEnemies = new List<IEnemy>();
        currentPlayers = new List<IPlayer>();
        platforms = new List<Platform>();
    }

    public void AddEnemy(IEnemy enemy)
    {
        currentEnemies.Add(enemy);
    }

    public void AddPlatform(Platform platform)
    {
        platforms.Add(platform);
    }

    public void AddPlayer(IPlayer player)
    {
        currentPlayers.Add(player);
    }
}
