package kotik.simple.client.warcraftlogs.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Rank {
    public final long total;
    public final Ranking rankings[];

    @JsonCreator
    public Rank(@JsonProperty("total") long total, @JsonProperty("rankings") Ranking[] rankings){
        this.total = total;
        this.rankings = rankings;
    }

    public static final class Ranking {
        public final String name;
        public final long total;
        public final long className;
        public final long spec;
        public final String guild;
        public final String server;
        public final String region;
        public final long duration;
        public final long startTime;
        public final long damageTaken;
        public final long deaths;
        public final long itemLevel;
        public final long patch;
        public final String reportID;
        public final long fightID;
        public final Team team[];
        public final long size;

        @JsonCreator
        public Ranking(@JsonProperty("name") String name, @JsonProperty("total") long total, @JsonProperty("class") long className, @JsonProperty("spec") long spec, @JsonProperty("guild") String guild, @JsonProperty("server") String server, @JsonProperty("region") String region, @JsonProperty("duration") long duration, @JsonProperty("startTime") long startTime, @JsonProperty("damageTaken") long damageTaken, @JsonProperty("deaths") long deaths, @JsonProperty("itemLevel") long itemLevel, @JsonProperty("patch") long patch, @JsonProperty("reportID") String reportID, @JsonProperty("fightID") long fightID, @JsonProperty("team") Team[] team, @JsonProperty("size") long size){
            this.name = name;
            this.total = total;
            this.className = className;
            this.spec = spec;
            this.guild = guild;
            this.server = server;
            this.region = region;
            this.duration = duration;
            this.startTime = startTime;
            this.damageTaken = damageTaken;
            this.deaths = deaths;
            this.itemLevel = itemLevel;
            this.patch = patch;
            this.reportID = reportID;
            this.fightID = fightID;
            this.team = team;
            this.size = size;
        }

        public static final class Team {
            public final String name;
            public final long className;
            public final long spec;

            @JsonCreator
            public Team(@JsonProperty("name") String name, @JsonProperty("class") long className, @JsonProperty("spec") long spec){
                this.name = name;
                this.className = className;
                this.spec = spec;
            }
        }
    }
}