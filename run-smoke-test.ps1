# PowerShell script to compile sources targeting Java 17 and run the smoke test
Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
$cwd = Split-Path -Parent $MyInvocation.MyCommand.Definition
Push-Location $cwd
try {
    if (Test-Path out) { Remove-Item -Recurse -Force out }
    New-Item -ItemType Directory -Path out | Out-Null
    $sources = Get-ChildItem -Recurse -File src\main\java\*.java,src\test\java\*.java | ForEach-Object { $_.FullName }
    javac --release 17 -encoding UTF-8 -d out $sources
    java -cp out ar.edu.uade.festival.service.FestivalSistemaTest
} finally {
    Pop-Location
}
