/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.druid.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.druid.segment.IndexSpec;
import org.apache.druid.segment.indexing.TuningConfigs;
import org.apache.druid.segment.writeout.SegmentWriteOutMediumFactory;
import org.joda.time.Duration;
import org.joda.time.Period;

import javax.annotation.Nullable;
import java.io.File;

/**
 * This class is copied from druid source code
 * in order to avoid adding additional dependencies on druid-indexing-service.
 */
@SuppressWarnings("ALL") @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") @JsonSubTypes({
    @JsonSubTypes.Type(name = "kafka", value = KafkaSupervisorTuningConfig.class) })
public class KafkaSupervisorTuningConfig extends KafkaTuningConfig {
  private final Integer workerThreads;
  private final Integer chatThreads;
  private final Long chatRetries;
  private final Duration httpTimeout;
  private final Duration shutdownTimeout;
  private final Duration offsetFetchPeriod;

  public KafkaSupervisorTuningConfig(@JsonProperty("maxRowsInMemory") Integer maxRowsInMemory,
      @JsonProperty("maxBytesInMemory") Long maxBytesInMemory,
      @JsonProperty("maxRowsPerSegment") Integer maxRowsPerSegment,
      @JsonProperty("maxTotalRows") Long maxTotalRows,
      @JsonProperty("intermediatePersistPeriod") Period intermediatePersistPeriod,
      @JsonProperty("basePersistDirectory") File basePersistDirectory,
      @JsonProperty("maxPendingPersists") Integer maxPendingPersists,
      @JsonProperty("indexSpec") IndexSpec indexSpec,
      // This parameter is left for compatibility when reading existing configs, to be removed in Druid 0.12.
      @JsonProperty("buildV9Directly") Boolean buildV9Directly,
      @JsonProperty("reportParseExceptions") Boolean reportParseExceptions,
      @JsonProperty("handoffConditionTimeout") Long handoffConditionTimeout,
      @JsonProperty("resetOffsetAutomatically") Boolean resetOffsetAutomatically,
      @JsonProperty("segmentWriteOutMediumFactory") @Nullable SegmentWriteOutMediumFactory segmentWriteOutMediumFactory,
      @JsonProperty("workerThreads") Integer workerThreads,
      @JsonProperty("chatThreads") Integer chatThreads,
      @JsonProperty("chatRetries") Long chatRetries,
      @JsonProperty("httpTimeout") Period httpTimeout,
      @JsonProperty("shutdownTimeout") Period shutdownTimeout,
      @JsonProperty("offsetFetchPeriod") Period offsetFetchPeriod,
      @JsonProperty("intermediateHandoffPeriod") Period intermediateHandoffPeriod,
      @JsonProperty("logParseExceptions") @Nullable Boolean logParseExceptions,
      @JsonProperty("maxParseExceptions") @Nullable Integer maxParseExceptions,
      @JsonProperty("maxSavedParseExceptions") @Nullable Integer maxSavedParseExceptions) {
    super(maxRowsInMemory,
        maxBytesInMemory,
        maxRowsPerSegment,
        maxTotalRows,
        intermediatePersistPeriod,
        basePersistDirectory,
        maxPendingPersists,
        indexSpec,
        true,
        reportParseExceptions,
        handoffConditionTimeout,
        resetOffsetAutomatically,
        segmentWriteOutMediumFactory,
        intermediateHandoffPeriod,
        logParseExceptions,
        maxParseExceptions,
        maxSavedParseExceptions);

    this.workerThreads = workerThreads;
    this.chatThreads = chatThreads;
    this.chatRetries = (chatRetries != null ? chatRetries : 8);
    this.httpTimeout = defaultDuration(httpTimeout, "PT10S");
    this.shutdownTimeout = defaultDuration(shutdownTimeout, "PT80S");
    this.offsetFetchPeriod = defaultDuration(offsetFetchPeriod, "PT30S");
  }

  @JsonProperty public Integer getWorkerThreads() {
    return workerThreads;
  }

  @JsonProperty public Integer getChatThreads() {
    return chatThreads;
  }

  @JsonProperty public Long getChatRetries() {
    return chatRetries;
  }

  @JsonProperty public Duration getHttpTimeout() {
    return httpTimeout;
  }

  @JsonProperty public Duration getShutdownTimeout() {
    return shutdownTimeout;
  }

  @JsonProperty public Duration getOffsetFetchPeriod() {
    return offsetFetchPeriod;
  }

  @Override public String toString() {
    return "KafkaSupervisorTuningConfig{"
        + "maxRowsInMemory="
        + getMaxRowsInMemory()
        + ", maxRowsPerSegment="
        + getMaxRowsPerSegment()
        + ", maxTotalRows="
        + getMaxTotalRows()
        + ", maxBytesInMemory="
        + TuningConfigs.getMaxBytesInMemoryOrDefault(getMaxBytesInMemory())
        + ", intermediatePersistPeriod="
        + getIntermediatePersistPeriod()
        + ", basePersistDirectory="
        + getBasePersistDirectory()
        + ", maxPendingPersists="
        + getMaxPendingPersists()
        + ", indexSpec="
        + getIndexSpec()
        + ", reportParseExceptions="
        + isReportParseExceptions()
        + ", handoffConditionTimeout="
        + getHandoffConditionTimeout()
        + ", resetOffsetAutomatically="
        + isResetOffsetAutomatically()
        + ", segmentWriteOutMediumFactory="
        + getSegmentWriteOutMediumFactory()
        + ", workerThreads="
        + workerThreads
        + ", chatThreads="
        + chatThreads
        + ", chatRetries="
        + chatRetries
        + ", httpTimeout="
        + httpTimeout
        + ", shutdownTimeout="
        + shutdownTimeout
        + ", offsetFetchPeriod="
        + offsetFetchPeriod
        + ", intermediateHandoffPeriod="
        + getIntermediateHandoffPeriod()
        + ", logParseExceptions="
        + isLogParseExceptions()
        + ", maxParseExceptions="
        + getMaxParseExceptions()
        + ", maxSavedParseExceptions="
        + getMaxSavedParseExceptions()
        + '}';
  }

  private static Duration defaultDuration(final Period period, final String theDefault) {
    return (period == null ? new Period(theDefault) : period).toStandardDuration();
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    KafkaSupervisorTuningConfig that = (KafkaSupervisorTuningConfig) o;

    if (workerThreads != null ? !workerThreads.equals(that.workerThreads) : that.workerThreads != null) {
      return false;
    }
    if (chatThreads != null ? !chatThreads.equals(that.chatThreads) : that.chatThreads != null) {
      return false;
    }
    if (chatRetries != null ? !chatRetries.equals(that.chatRetries) : that.chatRetries != null) {
      return false;
    }
    if (httpTimeout != null ? !httpTimeout.equals(that.httpTimeout) : that.httpTimeout != null) {
      return false;
    }
    if (shutdownTimeout != null ? !shutdownTimeout.equals(that.shutdownTimeout) : that.shutdownTimeout != null) {
      return false;
    }
    return offsetFetchPeriod != null ?
        offsetFetchPeriod.equals(that.offsetFetchPeriod) :
        that.offsetFetchPeriod == null;
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (workerThreads != null ? workerThreads.hashCode() : 0);
    result = 31 * result + (chatThreads != null ? chatThreads.hashCode() : 0);
    result = 31 * result + (chatRetries != null ? chatRetries.hashCode() : 0);
    result = 31 * result + (httpTimeout != null ? httpTimeout.hashCode() : 0);
    result = 31 * result + (shutdownTimeout != null ? shutdownTimeout.hashCode() : 0);
    result = 31 * result + (offsetFetchPeriod != null ? offsetFetchPeriod.hashCode() : 0);
    return result;
  }
}
