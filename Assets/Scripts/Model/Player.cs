using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player : IPlayer {

    private int health;
    private int attackPower;
    private int x;
    private int y;

    public int Attack() {
        return attackPower;
    }

    public void Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void TakeDamage(int damage) {
        this.health -= damage;
    }

}
