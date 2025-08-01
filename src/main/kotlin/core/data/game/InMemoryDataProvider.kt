package dev.deadzone.core.data.game

/**
 * In memory data provider
 *
 * Used to replace retrieving data directly from DB
 */
object InMemoryDataProvider : DataProvider {
    private val mockData = mapOf(
        "login_state.json" to loginState.trimIndent(),
        "cost_table.json" to costTable.trimIndent(),
        "srv_table.json" to SrvTable.trimIndent()
    )

    override fun loadRawJson(path: String): String {
        return mockData[path] ?: error("Missing mock data for: $path")
    }
}

const val SrvTable = """
{
  "fighter": {
    "id": "fighter",
    "maleUpper": "class_fighter",
    "maleLower": "class_fighter",
	"maleSkinOverlay": null,
    "femaleUpper": "class_fighter",
    "femaleLower": "class_fighter",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 1.0,
      "combatProjectile": 1.0,
      "combatMelee": 1.0,
      "combatImprovised": 1.0,
      "movement": 1.0,
      "scavenge": 1.0,
      "healing": 0.0,
      "trapSpotting": 0.0,
      "trapDisarming": 0.0
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [
        "assault_rifle",
        "lmg",
		"melee",
		"heavy"
      ],
      "types": []
    },
    "hideHair": false
  },
  "medic": {
    "id": "medic",
    "maleUpper": "class_medic",
    "maleLower": "class_medic",
	"maleSkinOverlay": null,
    "femaleUpper": "class_medic",
    "femaleLower": "class_medic",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 1.0,
      "combatProjectile": 0.6,
      "combatMelee": 0.7,
      "combatImprovised": 0.5,
      "movement": 1.1,
      "scavenge": 0.9,
      "healing": 1.5,
      "trapSpotting": 0.5,
      "trapDisarming": 0.5
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [
        "pistol",
        "smg"
      ],
      "types": [
        "BLADE"
      ]
    },
    "hideHair": false
  },
  "scavenger": {
    "id": "scavenger",
    "maleUpper": "class_scavenger",
    "maleLower": "class_scavenger",
	"maleSkinOverlay": null,
    "femaleUpper": "class_scavenger",
    "femaleLower": "class_scavenger",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 0.9,
      "combatProjectile": 0.5,
      "combatMelee": 0.6,
      "combatImprovised": 0.7,
      "movement": 1.4,
      "scavenge": 1.6,
      "healing": 0.3,
      "trapSpotting": 0.8,
      "trapDisarming": 0.5
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [
        "pistol",
        "shotgun",
		"bow"
      ],
      "types": [
        "BLUNT"
      ]
    },
    "hideHair": false
  },
  "engineer": {
    "id": "engineer",
    "maleUpper": "class_engineer",
    "maleLower": "class_engineer",
	"maleSkinOverlay": null,
    "femaleUpper": "class_engineer",
    "femaleLower": "class_engineer",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 1.0,
      "combatProjectile": 0.6,
      "combatMelee": 0.5,
      "combatImprovised": 0.9,
      "movement": 1.0,
      "scavenge": 1.0,
      "healing": 0.2,
      "trapSpotting": 1.2,
      "trapDisarming": 1.5
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [],
      "types": [
        "IMPROVISED"
      ]
    },
    "hideHair": false
  },
  "recon": {
    "id": "recon",
    "maleUpper": "class_recon",
    "maleLower": "class_recon",
	"maleSkinOverlay": null,
    "femaleUpper": "class_recon",
    "femaleLower": "class_recon",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 1.0,
      "combatProjectile": 1.4,
      "combatMelee": 0.7,
      "combatImprovised": 0.6,
      "movement": 1.5,
      "scavenge": 1.2,
      "healing": 0.1,
      "trapSpotting": 1.0,
      "trapDisarming": 0.8
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [
        "assault_rifle",
        "long_rifle"
      ],
      "types": [
        "BLADE"
      ]
    },
    "hideHair": true
  },
  "player": {
    "id": "player",
    "maleUpper": "body-upper-tshirtm",
    "maleLower": "body-lower-pantsm",
	"maleSkinOverlay": null,
    "femaleUpper": "body-upper-tshirtf",
    "femaleLower": "body-lower-skirtf",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 1.0,
      "combatProjectile": 1.0,
      "combatMelee": 1.0,
      "combatImprovised": 1.0,
      "movement": 1.0,
      "scavenge": 1.0,
      "healing": 1.0,
      "trapSpotting": 1.0,
      "trapDisarming": 1.0
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [
        "assault_rifle",
		"bow",
		"launcher",
		"long_rifle",
		"melee",
		"pistol",
		"shotgun",
		"smg",
		"lmg",
		"thrown",
		"heavy"
      ],
      "types": []
    },
    "hideHair": false
  },
  "unassigned": {
    "id": "unassigned",
    "maleUpper": "body-upper-tshirtm",
    "maleLower": "body-lower-pantsm",
	"maleSkinOverlay": null,
    "femaleUpper": "body-upper-tshirtf",
    "femaleLower": "body-lower-skirtf",
	"femaleSkinOverlay": null,
    "baseAttributes": {
      "health": 0,
      "combatProjectile": 0,
      "combatMelee": 0,
      "combatImprovised": 0,
      "movement": 0,
      "scavenge": 0,
      "healing": 0,
      "trapSpotting": 0,
      "trapDisarming": 0
    },
    "levelAttributes": {
      "health": 0.1,
      "combatProjectile": 0.05,
      "combatMelee": 0.05,
      "combatImprovised": 0.05,
      "movement": 0.03,
      "scavenge": 0.03,
      "healing": 0.02,
      "trapSpotting": 0.01,
      "trapDisarming": 0.01
    },
    "weapons": {
      "classes": [
        "rifle",
        "melee"
      ],
      "types": [
        "primary",
        "secondary"
      ]
    },
    "hideHair": false
  }
}
    
"""

const val costTable = """
{
  "BatchDisposal": {},
  "BatchRecycle": {},
  "constructionCosts": {},
  "CraftUpgradeItem": {},
  "CraftItem": {},
  "SurvivorReassign": {},
  "ArenaLaunch_<name>": {},
  "ResearchTask": {},
  "AllianceCreation": {},
  "SpeedUpInfectedBounty": {},
  "AllianceBannerEdit": {},
  "buy_coins_<service>": {},
  "InventoryUpgrade1": {},
  "InventoryUpgrade2": {},
  "InventoryUpgrade3": {},
  "AttributeReset": {},
  "TradeSlotUpgrade": {},
  "DeathMobileUpgrade": {},
  "TradeSlotUpgrade": {},
  "InventoryUpgrade1_UNUSED": {}
}
"""


const val loginState = """
{
  "settings": {},
  "news": {},
  "sales": [],
  "allianceWinnings": {},
  "recentPVPList": [],
  "invsize": 50,
  "upgrades": "",
  "allianceId": null,
  "allianceTag": null,
  "longSession": false,
  "leveledUp": false,
  "promos": [],
  "promoSale": null,
  "dealItem": null,
  "leaderResets": 0,
  "unequipItemBinds": [],
  "globalStats": {
    "idList": []
  },
  "inventory": [],
  "neighborHistory": {},
  "zombieAttack": false,
  "zombieAttackLogins": 0,
  "offersEnabled": false,
  "lastLogout": null,
  "prevLogin": null
}
"""
