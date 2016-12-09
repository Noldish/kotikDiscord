package kotik.simple.client.warcraftlogs.objects;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "encounter",
        "class",
        "spec",
        "guild",
        "rank",
        "outOf",
        "duration",
        "startTime",
        "reportID",
        "fightID",
        "difficulty",
        "size",
        "itemLevel",
        "total"
})
public class Shot {

    @JsonProperty("encounter")
    private Long encounter;
    @JsonProperty("class")
    private Long _class;
    @JsonProperty("spec")
    private Long spec;
    @JsonProperty("guild")
    private String guild;
    @JsonProperty("rank")
    private Long rank;
    @JsonProperty("outOf")
    private Long outOf;
    @JsonProperty("duration")
    private Long duration;
    @JsonProperty("startTime")
    private Long startTime;
    @JsonProperty("reportID")
    private String reportID;
    @JsonProperty("fightID")
    private Long fightID;
    @JsonProperty("difficulty")
    private Long difficulty;
    @JsonProperty("size")
    private Long size;
    @JsonProperty("itemLevel")
    private Long itemLevel;
    @JsonProperty("total")
    private Long total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The encounter
     */
    @JsonProperty("encounter")
    public Long getEncounter() {
        return encounter;
    }

    @Override
    public String toString() {
        return String.format("%20s - %5s из %-5s  ilvl:%s",Encounters.getType(encounter.intValue()),rank,outOf,itemLevel);
    }

    /**
     *
     * @param encounter
     * The encounter
     */
    @JsonProperty("encounter")
    public void setEncounter(Long encounter) {
        this.encounter = encounter;
    }

    /**
     *
     * @return
     * The _class
     */
    @JsonProperty("class")
    public Long getClass_() {
        return _class;
    }

    /**
     *
     * @param _class
     * The class
     */
    @JsonProperty("class")
    public void setClass_(Long _class) {
        this._class = _class;
    }

    /**
     *
     * @return
     * The spec
     */
    @JsonProperty("spec")
    public Long getSpec() {
        return spec;
    }

    /**
     *
     * @param spec
     * The spec
     */
    @JsonProperty("spec")
    public void setSpec(Long spec) {
        this.spec = spec;
    }

    /**
     *
     * @return
     * The guild
     */
    @JsonProperty("guild")
    public String getGuild() {
        return guild;
    }

    /**
     *
     * @param guild
     * The guild
     */
    @JsonProperty("guild")
    public void setGuild(String guild) {
        this.guild = guild;
    }

    /**
     *
     * @return
     * The rank
     */
    @JsonProperty("rank")
    public Long getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    @JsonProperty("rank")
    public void setRank(Long rank) {
        this.rank = rank;
    }

    /**
     *
     * @return
     * The outOf
     */
    @JsonProperty("outOf")
    public Long getOutOf() {
        return outOf;
    }

    /**
     *
     * @param outOf
     * The outOf
     */
    @JsonProperty("outOf")
    public void setOutOf(Long outOf) {
        this.outOf = outOf;
    }

    /**
     *
     * @return
     * The duration
     */
    @JsonProperty("duration")
    public Long getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     * The duration
     */
    @JsonProperty("duration")
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     * The startTime
     */
    @JsonProperty("startTime")
    public Long getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The startTime
     */
    @JsonProperty("startTime")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The reportID
     */
    @JsonProperty("reportID")
    public String getReportID() {
        return reportID;
    }

    /**
     *
     * @param reportID
     * The reportID
     */
    @JsonProperty("reportID")
    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    /**
     *
     * @return
     * The fightID
     */
    @JsonProperty("fightID")
    public Long getFightID() {
        return fightID;
    }

    /**
     *
     * @param fightID
     * The fightID
     */
    @JsonProperty("fightID")
    public void setFightID(Long fightID) {
        this.fightID = fightID;
    }

    /**
     *
     * @return
     * The difficulty
     */
    @JsonProperty("difficulty")
    public Long getDifficulty() {
        return difficulty;
    }

    /**
     *
     * @param difficulty
     * The difficulty
     */
    @JsonProperty("difficulty")
    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    /**
     *
     * @return
     * The size
     */
    @JsonProperty("size")
    public Long getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    @JsonProperty("size")
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The itemLevel
     */
    @JsonProperty("itemLevel")
    public Long getItemLevel() {
        return itemLevel;
    }

    /**
     *
     * @param itemLevel
     * The itemLevel
     */
    @JsonProperty("itemLevel")
    public void setItemLevel(Long itemLevel) {
        this.itemLevel = itemLevel;
    }

    /**
     *
     * @return
     * The total
     */
    @JsonProperty("total")
    public Long getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    @JsonProperty("total")
    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}