<p align="center">
  <img src="https://user-images.githubusercontent.com/21063304/211214783-90d24fac-ecc5-4eb0-802e-52a5ba26610a.png" alt="Legendchat"/>
</p>

<p align="center">
  Author: <a href="https://github.com/SubZero0">SubZero0</a>
  &nbsp;·&nbsp;
  API docs: <a href="https://dev.bukkit.org/projects/legendchat/pages/api">dev.bukkit.org</a>
</p>

> **Compatible with Minecraft 1.12 and newer.** The jar is Java 8 bytecode compiled against the
> 1.12 API, so the same build loads on legacy and on current servers.

## Introduction

A new channel-based chat plugin with high compatibility with other plugin's tags and an awesome
API for developers! Do you use Herochat but has found some really boring imperfections? Why don't
you try to use Legendchat?

**For server owners:** you will have the same design as Herochat and your players won't notice the
difference! And you can create a channel only for BungeeCord communication!

**For developers:** want to add a new prefix, suffix or remove some people from receiving a message
without removing others? Nice! We have an easy to use API!

## Main features

- Configurable chat channels (delay to send messages, cost ($) to send messages etc).
- Temporary channels (with permissions).
- Permissions to almost everything.
- Quick message (ex.: `/g <message>`).
- Private messages (`/tell`).
- Compatible with almost every chat tag.
- Update checker against this repository's GitHub releases (can be disabled in the config).

## Commands

| Command | Description |
| --- | --- |
| `/legendchat` | Display Legendchat help |
| `/channel <channel>` | Focus in other channel |
| `/tempchannel` | Temporary channel command |
| `/tell <player> [message]` | Send a private message or start a chat |
| `/reply <message>` | Reply the last private message |
| `/afk [motive]` | AFK mode (block private messages) |
| `/ignore <player>` | Ignore player messages and private messages |
| `/mute <channel>` | Ignore channel messages |

Aliases:

| Command | Aliases |
| --- | --- |
| `/legendchat` | `/lc` |
| `/channel` | `/ch` |
| `/tempchannel` | `/tc` |
| `/tell` | `/msg` `/pm` `/t` `/whisper` `/w` |
| `/reply` | `/r` |

## Permissions

<details>
<summary><b>Click to expand the full list</b></summary>

`<channel>` = channel name in lowercase &nbsp;·&nbsp; `<color>` = color name (like `blue`) or
format name (like `bold`) in lowercase

**Channels**

| Permission | Description |
| --- | --- |
| `legendchat.channel.<channel>.chat` | Read and write in the channel |
| `legendchat.channel.<channel>.focus` | Focus in the channel |
| `legendchat.channel.<channel>.free` | Bypass the message cost in the channel |
| `legendchat.channel.<channel>.nodelay` | Send messages without delay in the channel |
| `legendchat.channel.<channel>.blockwrite` | Block writing in the channel |
| `legendchat.channel.<channel>.blockmute` | Block muting the channel (new in V1.1.2) |

**Temporary channels**

| Permission | Description |
| --- | --- |
| `legendchat.tempchannel.manager` | Create and delete temporary channels |
| `legendchat.tempchannel.color` | Change your temporary channel color |
| `legendchat.tempchannel.user` | Join and leave temporary channels |
| `legendchat.tempchannel.admin` | Full access to the temporary channel commands |

**Colors**

| Permission | Description |
| --- | --- |
| `legendchat.color.<color>` | Use a certain color or format in channels |
| `legendchat.color.allcolors` | Use all colors in channels |
| `legendchat.color.allformats` | Use all formats in channels |

**Restrictions**

| Permission | Description |
| --- | --- |
| `legendchat.block.tell` | Block the player from sending private messages |
| `legendchat.block.locktell` | Block the player from chatting with `/tell <player>` |
| `legendchat.block.reply` | Block the player from replying private messages |
| `legendchat.block.afk` | Block the player from using `/afk` |
| `legendchat.block.afkmotive` | Block the player from setting an afk message |
| `legendchat.block.ignore` | Block the player from being ignored |

**Administration**

| Permission | Description |
| --- | --- |
| `legendchat.admin` | Admin permission, unlocks everything |
| `legendchat.admin.tempchannel` | Use `/lc deltc` |
| `legendchat.admin.playerch` | Use `/lc playerch` |
| `legendchat.admin.channel` | Use `/lc channel` |
| `legendchat.admin.spy` | Use `/lc spy` |
| `legendchat.admin.hide` | Use `/lc hide` |
| `legendchat.admin.mute` | Use `/lc mute` |
| `legendchat.admin.unmute` | Use `/lc unmute` |
| `legendchat.admin.muteall` | Use `/lc muteall` |
| `legendchat.admin.unmuteall` | Use `/lc unmuteall` |
| `legendchat.admin.reload` | Use `/lc reload` |

</details>

## Available channel colors

<details>
<summary><b>Click to expand the full list</b></summary>

`BLACK` · `DARKBLUE` · `DARKGREEN` · `DARKAQUA` · `DARKRED` · `DARKPURPLE` · `GOLD` · `GRAY`
· `DARKGRAY` · `BLUE` · `GREEN` · `AQUA` · `RED` · `LIGHTPURPLE` · `YELLOW` · `WHITE`

Any unrecognized name falls back to `WHITE`.

</details>

## Building

Requires Maven and a JDK 8 or newer:

```
mvn clean package
```

The result is `target/Legendchat.jar`, already containing `plugin.yml`, the default configuration
and the language files.
