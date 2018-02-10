using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public interface IPlayer {

    //player takes the given damage
    void TakeDamage(int damage);

    //player attacks the returned damage
    int Attack();

    /// <summary>
    /// moves player according to the given coordinates
    /// </summary>
    /// <param name="x"></param> x coordinate
    /// <param name="y"></param> y coordinate
    void Move(int x, int y);
}
