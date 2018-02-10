using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//represents the functionality of an Enemy Object
public interface IEnemy {
    //takes off the given amount from the health of this enemy
    void TakeDamage(int damage);

    //perform attack which does int returned damage
    int Attack();

    //move an enemy to the given coordinates.
    void Move(int x, int y);
}
