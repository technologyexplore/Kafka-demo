# Kafka-demo
Kafka技术分享

	1，Kafka 产生的背景

    2，Kafka 安装部署及集群部署

    3，Kafka 的应用

    4，Kafka的核心-Topic&Partition

          Topic属于逻辑概念，真实存储在Partition；一个Topic下划分多个Partition

    5，消息分发策略和消费原理

          存在两种分区分配策略，一种是Range(默认)、 另 一种是RoundRobin(轮询)；通过partition.assignment.strategy这个参数来设置；

    6，消息的存储策略

          Partition 是以文件的形式存储在文件系统中，比如创建一个名为 firstTopic 的 topic，其中有 3 个 partition，那么在kafka 的数据目录（/tmp/kafka-log）中就有 3 个目录，firstTopic-0~3， 命名规则是<topic_name>-<partition_id> 