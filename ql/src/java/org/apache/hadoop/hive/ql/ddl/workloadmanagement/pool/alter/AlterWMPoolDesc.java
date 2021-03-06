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

package org.apache.hadoop.hive.ql.ddl.workloadmanagement.pool.alter;

import java.io.Serializable;

import org.apache.hadoop.hive.ql.ddl.DDLDesc;
import org.apache.hadoop.hive.ql.plan.Explain;
import org.apache.hadoop.hive.ql.plan.Explain.Level;

/**
 * DDL task description for ALTER POOL commands.
 */
@Explain(displayName = "Alter Pool", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
public class AlterWMPoolDesc implements DDLDesc, Serializable {
  private static final long serialVersionUID = 4872940135771213510L;

  private final String resourcePlanName;
  private final String poolPath;
  private final Double allocFraction;
  private final Integer queryParallelism;
  private final String schedulingPolicy;
  private final boolean removeSchedulingPolicy;
  private final String newPath;

  public AlterWMPoolDesc(String resourcePlanName, String poolPath, Double allocFraction, Integer queryParallelism,
      String schedulingPolicy, boolean removeSchedulingPolicy, String newPath) {
    this.resourcePlanName = resourcePlanName;
    this.poolPath = poolPath;
    this.allocFraction = allocFraction;
    this.queryParallelism = queryParallelism;
    this.schedulingPolicy = schedulingPolicy;
    this.removeSchedulingPolicy = removeSchedulingPolicy;
    this.newPath = newPath;
  }

  @Explain(displayName = "Resource plan name", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getResourcePlanName() {
    return resourcePlanName;
  }

  @Explain(displayName = "Pool path", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getPoolPath() {
    return poolPath;
  }

  @Explain(displayName = "Alloc fraction", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public Double getAllocFraction() {
    return allocFraction;
  }

  @Explain(displayName = "Query parallelism", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public Integer getQueryParallelism() {
    return queryParallelism;
  }

  @Explain(displayName = "Scheduling policy", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getSchedulingPolicy() {
    return schedulingPolicy;
  }

  @Explain(displayName = "Remove scheduling policy", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED },
      displayOnlyOnTrue=true)
  public boolean isRemoveSchedulingPolicy() {
    return removeSchedulingPolicy;
  }

  @Explain(displayName = "New path", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getNewPath() {
    return newPath;
  }
}
