

# Configurando o Jacoco
Primeiro, adicione ao build.gradle de nível de projeto:
```groovy
buildscript {
 dependencies { //... classpath 'com.dicedmelon.gradle:jacoco-android:0.1.4' }}
```

Em seguida crie um arquivo chamado `./jacoco.gradle` e adicione essas linhas a ela:
```groovy
jacocoAndroidUnitTestReport {
  excludes += ['**/AutoValue_*.*',
                 '**/*JavascriptBridge.class',
                 '**/MainPresenter2**',
                 '**/*Activity*']
  csv.enabled false
  html.enabled true
xml.enabled true }
```

Depois adicione no build.gradle do seu módulo principal/base (normalmente chamado de :app), as seguintes linhas abaixo do apply dos outros plugins:

```groovy
apply plugin: 'jacoco-android'
apply from: './jacoco.gradle'

jacoco {
toolVersion = "0.8.4" }
```
Com essa configuração você pode rodar o cover de teste unitário e visualizar no arquivo index.html na pasta app/build/reports/jacoco/jacocoTest{nome da sua flavor}UnitTestReport/html/index.html
```bash
./gradlew jacocoTest{nome da sua flavor}gUnitTestReport ```

# Adicione o plugin do SonarQube
  Primeiro, adicione ao build.gradle de nível de projeto:
  ```groovy
buildscript {
 repositories { //... maven { url "https://plugins.gradle.org/m2/" } }}
 ``````groovy
buildscript {
 dependencies { //... classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.4.RELEASE")   classpath "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8"
    }
}
```
```groovy
allprojects {
 repositories { //... maven { url "https://plugins.gradle.org/m2/" } }}
```
Em seguida crie um arquivo chamado `./sonarqube.gradle` e adicione essas linhas a ela:
obs.: ver o caminho do .xml gerado pelo jacoco (muda conforme a flavor), e configure corretamente os itens: sonar.coverage.jacoco.xmlReportPaths e sonar.junit.reportsPath
```groovy
sonarqube {
  properties {
  property "sonar.projectName", "made-sonar-qube-test-android" //nome que será exibido no dashboard do SonarQube
  property "sonar.projectKey", "made-sonar-qube-test-android" //chave/id único do projeto no dashboard do SonarQube
  property "sonar.host.url", "http://127.0.0.1:9000/" //endereço do seu server do SonarQube
  property "sonar.language", "java" //não percebi nenhuma diferença ao trocar para kotlin
  property "sonar.login", "username" //nome de usuário para logar com funções de adm para poder subir os projetos e atualizações
  property "sonar.password", "senha" //senha desse usuario

  property "sonar.sources", "src/main/" //quais são os arquivos .java e .kt daonde o SonarQube vai tirar as métricas
 //property "sonar.sources", "app/src/main/, splash/src/main" //caso vc tenha mais de um caminho (de outros módulos) para analisar  property "sonar.java.binaries", "build/tmp/kotlin-classes" //no caso do kotlin, é o caminho da onde o binários das classes javes são extraídos. Não sei pra q server ainda
  property "sonar.tests", "src/test" //O caminho para os testes unitários. Mas não sei pra q server ainda já que os relatórios de cobertura são extraídos do jacoco

 //Configuração da parte do Jacoco. Não precisa ter essas linhas caso o teste de coverage não for necessário // ver o caminho do xml gerado pelo jacoco (muda conforme a flavor)  property "sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/jacocoTestDebugUnitTestReport/jacocoTestDebugUnitTestReport.xml"
  // ver o caminho do xml gerado pelo jacoco (muda conforme a flavor)
  property "sonar.junit.reportsPath", "build/reports/jacoco/jacocoTestDebugUnitTestReport/"
  property "sonar.java.coveragePlugin", "jacoco"
  property "sonar.android.lint.report", "build/reports/lint-results.xml"
} }
```

Depois adicione no build.gradle do seu módulo principal/base (normalmente chamado de :app), as seguintes linhas abaixo do apply dos outros plugins:

```groovy
apply plugin: "org.sonarqube"
apply from: './sonarqube.gradle'
```
Com essa configuração você pode rodar os cover de teste unitário e subir o projeto no SonarQube rodando:
```bash
./gradlew jacocoTestProdDebugUnitTestReport sonarqube
```
Obs.: O comando muda conforme a flavor, rode o comando: ```bash
./gradlew tasks
```
Para obter o nome da task corretamente


Com essa configuração você pode subir o projeto no SonarQube rodando:
```bash
./gradlew sonarqube
```

É possível reconfigurar o arquivo sonar, pra contar cover de teste de UI, após fazer as devidas configurações,você pode subir o projeto no SonarQube e com os testes de cobertura Unitário + Interface rodando:
```bash
./gradlew clean connectedAndroidTest test createDebugCoverageReport combinedTestReportDebug sonarqube
```

Dependendo dos productFlavors que vc tiver, esses comandos tem que ser alterados.

# Conclusão
O cover fica um pouco diferente do jacoco e do sonar, acredito, que o sonar pega as duas colunas do jacoco missed instructions e missed branches e faz uma médias (missed instructions + missed branches) / 2.
Desculpem, foi o mais perto que consegui chegar de fazer um bom trabalho.