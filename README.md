# Kore

A great plugin for all the functions needed for a minecraft server

## Info

Native version: 1.20

Tested versions: 1.8, 1.9, 1.10, 1.11. 1.12, 1.13, 1.14, 1.15, 1.16, 1.17, 1.18, 1.19, 1.20, 1.21

## Commands

(mandatory) [optional]

- /kore (help|reload|info)
- /gamemode (gamemode) [player]
- /gmc [player]
- /gms [player]
- /gma [player]
- /gmsp [player]
- /fly [player]
- /teleport /tp (player| x y z) [player| x y z]
- /heal [player]
- /god [player]
- /vanish /v [player]
- /setspawn
- /spawn [player]
- /trash /disposal
- /orbitalcannon (player | x y z)
- /smite (player)
- /kill [player]
- /warp (add|remove) (name)
- /warp (name) [player]
- /home [set|remove]
- /speed (value) [player] [type]
- /killmobs
- /spawnmob (mob) [number]
- /tpa (accept|deny|player_name)

## Permissions

- kore.gamemode.adventure
- kore.gamemode.spectator
- kore.gamemode.creative
- kore.gamemode.survival
- kore.orbitalcannon
- kore.spawn.other
- kore.chatbypass
- kore.warp.admin
- kore.gamemode.*
- kore.teleport
- kore.gamemode
- kore.setspawn
- kore.killmobs
- kore.godmode
- kore.reload
- kore.vanish
- kore.spawn
- kore.trash
- kore.smite
- kore.speed
- kore.heal
- kore.kill
- kore.warp
- kore.home
- kore.tpa
- kore.tp
- kore.*

## Placeholders

### [FOR PLACEHOLDERS NEED PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

- %kore_vanished%
- %kore_flying%
- %kore_godmode%

​
[BStats](https://bstats.org/plugin/bukkit/Kore/18653)

​
[FOR ANY ISUES GO TO DISCORD FORUM](https://discord.gg/sR5QTmHQ2U)

## Configs and Languages

### Config.yml

```yaml
gamemode:
  enabled: true
fly:
  enabled: true
teleport:
  enabled: true
godmode:
  enabled: true
heal:
  enabled: true
vanish:
  enabled: true
spawn:
  enabled: true
  on-join: true
  on-death: true
  on-void: true

  worlds:
    blacklist: true
    list:
      - world1
      - world2

trash:
  enabled: true
orbitalcannon:
  enabled: true
  tell-to-victim: true
smite:
  enabled: true
kill:
  enabled: true
warp:
  enabled: true
home:
  enabled: true
speed:
  enabled: true
killmobs:
  enabled: true
  async: true #recomanded: true
spawnmob:
  enabled: true
  async: true #recomanded: true
tpa:
  enabled: true
  expiration: 60 #in seconds

chat:
  enabled: true
  format: "%sender%: %message%"
  blacklist-words:
    - fuck
    - shit
messages: en

server:
  # Make it null if or leave it empty you want to disable join messages
  join-msg: "&8[&a+&8] &f%player%"
  leave-msg: "&8[&c-&8] &f%player%"

world-manipulator:
  enable: false
  rules:
    anti-hunger: true
    anti-weather: true
    anti-join-message: true
    anti-mob-spawn: true

  worlds:
    blacklist: true
    list:
      - world1
      - world2
```
