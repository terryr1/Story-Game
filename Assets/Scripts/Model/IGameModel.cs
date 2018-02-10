using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public interface IGameModel {

    //add platforms to the model
    void AddPlatform(Platform platform);

    //add enemies to the model
    void AddEnemy(IEnemy enemy);

    //add players to the model
    void AddPlayer(IPlayer player);
   
}
