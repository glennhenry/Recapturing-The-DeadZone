package dev.deadzone.core.model.data

import dev.deadzone.core.data.HardcodedData
import kotlinx.serialization.Serializable
import dev.deadzone.core.model.data.user.AbstractUser
import dev.deadzone.core.model.game.data.Attributes
import dev.deadzone.core.model.game.data.BuildingCollection
import dev.deadzone.core.model.game.data.quests.DynamicQuest
import dev.deadzone.core.model.game.data.GameResources
import dev.deadzone.core.model.game.data.BatchRecycleJob
import dev.deadzone.core.model.game.data.Building
import dev.deadzone.core.model.game.data.EffectCollection
import dev.deadzone.core.model.game.data.bounty.InfectedBounty
import dev.deadzone.core.model.game.data.Inventory
import dev.deadzone.core.model.game.data.MissionData
import dev.deadzone.core.model.game.data.Survivor
import dev.deadzone.core.model.game.data.SurvivorClassConstants_Constants
import dev.deadzone.core.model.network.RemotePlayerData
import dev.deadzone.core.model.game.data.research.ResearchState
import dev.deadzone.core.model.game.data.SurvivorCollection
import dev.deadzone.core.model.game.data.SurvivorLoadoutEntry
import dev.deadzone.core.model.game.data.Task
import dev.deadzone.core.model.game.data.TaskCollection
import dev.deadzone.core.model.game.data.assignment.AssignmentData
import dev.deadzone.core.model.game.data.effects.Effect
import dev.deadzone.core.model.game.data.quests.GQDataObj
import dev.deadzone.core.model.game.data.skills.SkillState
import io.ktor.util.date.getTimeMillis
import kotlin.experimental.or

@Serializable
data class PlayerData(
    val key: String,
    val user: Map<String, AbstractUser> = mapOf(),
    val admin: Boolean,
    val allianceId: String,
    val allianceTag: String,
    val flags: ByteArray?,  // deserialized to flagset (see PlayerFlags), indicates tutorial stuff
    val upgrades: ByteArray?,      // deserialized to flagset
    val nickname: String,
    val playerSurvivor: String,
    val levelPts: UInt = 0u,
    val restXP: Int = 0,
    val oneTimePurchases: List<String> = listOf(),
    val neighbors: Map<String, RemotePlayerData>?,
    val friends: Map<String, RemotePlayerData>?,
    val neighborHistory: Map<String, RemotePlayerData>?,
    val research: ResearchState?,
    val skills: Map<String, SkillState>?,
    val resources: GameResources,
    val survivors: List<Survivor>,
    val playerAttributes: Attributes,
    val buildings: List<Building>,
    val rally: Map<String, List<String>>?,  // key building id, value list of survivor ids
    val tasks: List<Task>,
    val missions: List<MissionData>?,
    val assignments: List<AssignmentData>?,
    val inventory: Inventory? = null,
    val effects: List<ByteArray>?, // can also be map<string, string>
    val globalEffects: List<ByteArray>?, // can also be map<string, string>
    val cooldowns: Map<String, ByteArray>?,
    val batchRecycles: List<BatchRecycleJob>?,
    val offenceLoadout: Map<String, SurvivorLoadoutEntry>?,
    val defenceLoadout: Map<String, SurvivorLoadoutEntry>?,
    val quests: ByteArray?,  // parsed by booleanArrayFromByteArray
    val questsCollected: ByteArray?,  // parsed by booleanArrayFromByteArray
    val achievements: ByteArray?,  // parsed by booleanArrayFromByteArray
    val dailyQuest: ByteArray?,   // parsed to DynamicQuest via constructor
    val questsTracked: String?,  // each quest separated with |
    val gQuestsV2: Map<String, GQDataObj>?,
    val bountyCap: Int,
    val lastLogout: Long?,
    val dzBounty: InfectedBounty?,
    val nextDZBountyIssue: Long,
    val highActivity: HighActivity?,  // unknown which class is this so we make custom class
    val invsize: Int,
    val zombieAttack: Boolean,
    val zombieAttackLogins: Int,
    val offersEnabled: Boolean,
    val prevLogin: PrevLogin?,
    val lastLogin: Long?,
    val notifications: List<Notification?>?,
) {
    companion object {
        fun dummy(): PlayerData {
            val exampleBools = IntRange(0, 8).map { false }
            val exampleBools2 = listOf(true, false, true, true, true, true, true, true, true, true, true)

            return PlayerData(
                key = "exampleKey",
                admin = true,
                allianceId = "",
                allianceTag = "",
                flags = boolsToByteArray(exampleBools2),
                upgrades = FlagSet.mockFlagSetByteArray(),
                nickname = "dzplayer",
                playerSurvivor = HardcodedData.PLAYER_SRV_ID,
                neighbors = null,
                friends = null,
                neighborHistory = null,
                research = ResearchState(active = listOf(), mapOf()),
                skills = null,
                resources = GameResources(
                    cash = 100000,
                    wood = 99999,
                    metal = 99999,
                    cloth = 99999,
                    food = 200,
                    water = 200,
                    ammunition = 99999
                ),
                survivors = SurvivorCollection.threeSurvivors(),
                playerAttributes = Attributes.dummy(),
                buildings = BuildingCollection.goodBase(),
                rally = mapOf(
                    // depends on BuildingCollection!
                    "B23" to listOf(HardcodedData.FIGHTER_SRV_ID),
                    "B45" to listOf(HardcodedData.RECON_SRV_ID),
                    "B52" to listOf(HardcodedData.PLAYER_SRV_ID),
                ),
                tasks = TaskCollection().list,
                missions = listOf(MissionData.dummy(HardcodedData.PLAYER_SRV_ID)),
                assignments = null,
//                inventory = null,
                effects = listOf(Effect.halloweenTrickPumpkinZombie(), Effect.halloweenTrickPewPew()),
                globalEffects = listOf(Effect.halloweenTrickPumpkinZombie(), Effect.halloweenTrickPewPew()),
                cooldowns = null,
                batchRecycles = null,
                offenceLoadout = mapOf(
                    HardcodedData.PLAYER_SRV_ID to SurvivorLoadoutEntry.playerLoudout(),
                    HardcodedData.FIGHTER_SRV_ID to SurvivorLoadoutEntry.fighterLoadout(),
                    HardcodedData.RECON_SRV_ID to SurvivorLoadoutEntry.reconLoadout(),
                ),
                defenceLoadout = mapOf(
                    HardcodedData.PLAYER_SRV_ID to SurvivorLoadoutEntry.playerLoudout(),
                    HardcodedData.FIGHTER_SRV_ID to SurvivorLoadoutEntry.fighterLoadout(),
                    HardcodedData.RECON_SRV_ID to SurvivorLoadoutEntry.reconLoadout(),
                ),
                quests = boolsToByteArray(exampleBools),
                questsCollected = boolsToByteArray(exampleBools),
                achievements = boolsToByteArray(exampleBools),
                dailyQuest = null,
                questsTracked = null,
                gQuestsV2 = null,
                bountyCap = 0,
                lastLogout = getTimeMillis() - 100000,
                dzBounty = null,
                nextDZBountyIssue = 1230768000000,
                highActivity = null,
                invsize = 16,
                zombieAttack = false,
                zombieAttackLogins = 0,
                offersEnabled = false,
                prevLogin = PrevLogin(getTimeMillis() - 100000),
                lastLogin = getTimeMillis() - 100000,
                notifications = null,
            )
        }

        private fun boolsToByteArray(bools: List<Boolean>): ByteArray? {
            val byteCount = (bools.size + 7) / 8
            val bytes = ByteArray(byteCount)

            for (i in bools.indices) {
                if (bools[i]) {
                    val byteIndex = i / 8
                    val bitIndex = i % 8
                    bytes[byteIndex] = bytes[byteIndex] or (1 shl bitIndex).toByte()
                }
            }

            return bytes
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerData

        if (admin != other.admin) return false
        if (restXP != other.restXP) return false
        if (bountyCap != other.bountyCap) return false
        if (lastLogout != other.lastLogout) return false
        if (nextDZBountyIssue != other.nextDZBountyIssue) return false
        if (invsize != other.invsize) return false
        if (zombieAttack != other.zombieAttack) return false
        if (zombieAttackLogins != other.zombieAttackLogins) return false
        if (offersEnabled != other.offersEnabled) return false
        if (lastLogin != other.lastLogin) return false
        if (key != other.key) return false
        if (user != other.user) return false
        if (allianceId != other.allianceId) return false
        if (allianceTag != other.allianceTag) return false
        if (!flags.contentEquals(other.flags)) return false
        if (!upgrades.contentEquals(other.upgrades)) return false
        if (nickname != other.nickname) return false
        if (playerSurvivor != other.playerSurvivor) return false
        if (levelPts != other.levelPts) return false
        if (oneTimePurchases != other.oneTimePurchases) return false
        if (neighbors != other.neighbors) return false
        if (friends != other.friends) return false
        if (neighborHistory != other.neighborHistory) return false
        if (research != other.research) return false
        if (skills != other.skills) return false
        if (resources != other.resources) return false
        if (survivors != other.survivors) return false
        if (playerAttributes != other.playerAttributes) return false
        if (buildings != other.buildings) return false
        if (rally != other.rally) return false
        if (tasks != other.tasks) return false
        if (missions != other.missions) return false
        if (assignments != other.assignments) return false
        if (inventory != other.inventory) return false
        if (effects != other.effects) return false
        if (globalEffects != other.globalEffects) return false
        if (cooldowns != other.cooldowns) return false
        if (batchRecycles != other.batchRecycles) return false
        if (offenceLoadout != other.offenceLoadout) return false
        if (defenceLoadout != other.defenceLoadout) return false
        if (!quests.contentEquals(other.quests)) return false
        if (!questsCollected.contentEquals(other.questsCollected)) return false
        if (!achievements.contentEquals(other.achievements)) return false
        if (dailyQuest != other.dailyQuest) return false
        if (questsTracked != other.questsTracked) return false
        if (gQuestsV2 != other.gQuestsV2) return false
        if (dzBounty != other.dzBounty) return false
        if (highActivity != other.highActivity) return false
        if (prevLogin != other.prevLogin) return false
        if (notifications != other.notifications) return false

        return true
    }

    override fun hashCode(): Int {
        var result = admin.hashCode()
        result = 31 * result + restXP
        result = 31 * result + bountyCap
        result = 31 * result + (lastLogout?.hashCode() ?: 0)
        result = 31 * result + nextDZBountyIssue.hashCode()
        result = 31 * result + invsize
        result = 31 * result + zombieAttack.hashCode()
        result = 31 * result + zombieAttackLogins
        result = 31 * result + offersEnabled.hashCode()
        result = 31 * result + (lastLogin?.hashCode() ?: 0)
        result = 31 * result + key.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + allianceId.hashCode()
        result = 31 * result + allianceTag.hashCode()
        result = 31 * result + (flags?.contentHashCode() ?: 0)
        result = 31 * result + (upgrades?.contentHashCode() ?: 0)
        result = 31 * result + nickname.hashCode()
        result = 31 * result + playerSurvivor.hashCode()
        result = 31 * result + levelPts.hashCode()
        result = 31 * result + oneTimePurchases.hashCode()
        result = 31 * result + (neighbors?.hashCode() ?: 0)
        result = 31 * result + (friends?.hashCode() ?: 0)
        result = 31 * result + (neighborHistory?.hashCode() ?: 0)
        result = 31 * result + (research?.hashCode() ?: 0)
        result = 31 * result + (skills?.hashCode() ?: 0)
        result = 31 * result + resources.hashCode()
        result = 31 * result + survivors.hashCode()
        result = 31 * result + playerAttributes.hashCode()
        result = 31 * result + buildings.hashCode()
        result = 31 * result + (rally?.hashCode() ?: 0)
        result = 31 * result + tasks.hashCode()
        result = 31 * result + (missions?.hashCode() ?: 0)
        result = 31 * result + (assignments?.hashCode() ?: 0)
        result = 31 * result + (inventory?.hashCode() ?: 0)
        result = 31 * result + (effects?.hashCode() ?: 0)
        result = 31 * result + (globalEffects?.hashCode() ?: 0)
        result = 31 * result + (cooldowns?.hashCode() ?: 0)
        result = 31 * result + (batchRecycles?.hashCode() ?: 0)
        result = 31 * result + (offenceLoadout?.hashCode() ?: 0)
        result = 31 * result + (defenceLoadout?.hashCode() ?: 0)
        result = 31 * result + (quests?.contentHashCode() ?: 0)
        result = 31 * result + (questsCollected?.contentHashCode() ?: 0)
        result = 31 * result + (achievements?.contentHashCode() ?: 0)
        result = 31 * result + (dailyQuest?.hashCode() ?: 0)
        result = 31 * result + (questsTracked?.hashCode() ?: 0)
        result = 31 * result + (gQuestsV2?.hashCode() ?: 0)
        result = 31 * result + (dzBounty?.hashCode() ?: 0)
        result = 31 * result + (highActivity?.hashCode() ?: 0)
        result = 31 * result + (prevLogin?.hashCode() ?: 0)
        result = 31 * result + (notifications?.hashCode() ?: 0)
        return result
    }
}
