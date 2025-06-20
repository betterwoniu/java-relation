FROM openjdk:17-jdk-slim AS  build

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY setting.xml .

# 清理并下载依赖（利用缓存）
RUN rm -rf /root/.m2/repository/* && \
    ./mvnw dependency:go-offline -B -s setting.xml

# 复制源代码并构建
COPY src ./src
RUN ./mvnw install -DskipTests -s setting.xml

# 运行阶段
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar springboot-grpc-clent.jar
EXPOSE 8088
CMD ["java", "-jar", "springboot-grpc-clent.jar"]