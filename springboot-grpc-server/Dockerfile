FROM maven:3.6.3 AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY settings.xml .
COPY pom.xml .
COPY ./ ./

RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests -s settings.xml


FROM openjdk:8-jdk-alpine

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

COPY --from=builder /workspace/app/target/*.jar springboot-grpc-server.jar


# 暴露端口（与application.yml中配置一致）
EXPOSE 8089



# 健康检查（Spring Boot Actuator需启用）
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8089/actuator/health || exit 1

# 优化JVM参数（根据容器内存自动调整）
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+ExitOnOutOfMemoryError"
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/springboot-grpc-server.jar"]