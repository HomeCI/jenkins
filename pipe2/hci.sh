#!/bin/bash
export CIP_REPO_PATH="/Users/alvaroperis/ArchLab/dockerManager/corex"
export CIP_CORE_PATH="/Users/alvaroperis/ArchLab/dockerManager/repos"

 #--- tool/docker.sh --- 

# Verificar que CIP_REPO_PATH y CIP_CORE_PATH estén definidas
if [[ -z "${CIP_REPO_PATH}" || -z "${CIP_CORE_PATH}" ]]; then
    echo "Error: CIP_REPO_PATH y/o CIP_CORE_PATH no están definidas en las variables de entorno."
    exit 1
fi

REPO_PATHS=("$CIP_REPO_PATH" "$CIP_CORE_PATH")

function start() {
  repo_name=$1
  for path in "${REPO_PATHS[@]}"; do
    if [[ -d "$path/$repo_name" ]]; then
      docker compose -f "$path/$repo_name/docker-compose.yml" up -d
      return 0
    fi
  done
  echo "No se encontró el repositorio $repo_name"
  return 1
}

function stop() {
  repo_name=$1
  for path in "${REPO_PATHS[@]}"; do
    if [[ -d "$path/$repo_name" ]]; then
      docker compose -f "$path/$repo_name/docker-compose.yml" stop
      return 0
    fi
  done
  echo "No se encontró el repositorio $repo_name"
  return 1
}

function build() {
  repo_name=$1
  for path in "${REPO_PATHS[@]}"; do
    if [[ -d "$path/$repo_name" ]]; then
      docker compose -f "$path/$repo_name/docker-compose.yml" up -d --build
      return 0
    fi
  done
  echo "No se encontró el repositorio $repo_name"
  return 1
}

function status() {
  docker ps --format "table {{.ID}}\t{{.Names}}\t{{.CreatedAt}}\t{{.Status}}\t{{.Ports}}"
}

function logs() {
  container_name=$1
  container_id=$(docker ps -q --filter "name=$container_name")
  if [[ -z "$container_id" ]]; then
    echo "No se encontró el contenedor $container_name"
    return 1
  fi
  docker logs "$container_id"
}

function exec() {
  container_name=$1
  container_id=$(docker ps -q --filter "name=$container_name")
  if [[ -z "$container_id" ]]; then
    echo "No se encontró el contenedor $container_name"
    return 1
  fi
  docker exec -it "$container_id" /bin/bash
}

function list() {
  echo "REPOSITORIOS"
  echo "-------------"
  for path in "${REPO_PATHS[@]}"; do
    echo "En $path:"
    for dir in "$path"/*; do
      if [[ -d "$dir" ]]; then
        echo "- $(basename "$dir")"
      fi
    done
    echo ""
  done
}
 #--- tool/help.sh --- 
function show_help() {
    echo "Uso: $0 <comando> [repositorio]"
    echo ""
    echo "Comandos:"
    echo "  start    Inicia el contenedor del repositorio indicado."
    echo "  stop     Detiene el contenedor del repositorio indicado."
    echo "  build    Crea y arranca el contenedor del repositorio indicado."
    echo "  status   Muestra el estado de todos los contenedores."
    echo "  logs     Muestra los logs del contenedor indicado."
    echo "  exec     Abre una terminal en el contenedor indicado."
    echo "  list     Lista los repositorios disponibles."
    echo ""
    echo "Opciones:"
    echo "  repositorio     El nombre del repositorio a ejecutar."
    echo ""
    echo "Ejemplos:"
    echo "  $0 start mi_repositorio"
    echo "  $0 logs mi_repositorio"
    echo "  $0 exec mi_repositorio"
    echo "  $0 list"
}
 #--- tool/cihome.sh --- 

if [[ "$#" -lt 1 ]]; then
  show_help
  exit 1
fi

case "$1" in
  start)
    if [[ "$#" -ne 2 ]]; then
      echo "Uso: $0 start <nombre-repositorio>"
      exit 1
    fi
    start "$2"
    ;;
  stop)
    if [[ "$#" -ne 2 ]]; then
      echo "Uso: $0 stop <nombre-repositorio>"
      exit 1
    fi
    stop "$2"
    ;;
  build)
    if [[ "$#" -ne 2 ]]; then
      echo "Uso: $0 build <nombre-repositorio>"
      exit 1
    fi
    build "$2"
    ;;
  status)
    status
    ;;
  logs)
    if [[ "$#" -ne 2 ]]; then
      echo "Uso: $0 logs <nombre-repositorio>"
      exit 1
    fi
    logs $2
    ;;
  exec)
    if [[ "$#" -ne 2 ]]; then
      echo "Uso: $0 exec <nombre-repositorio>"
      exit 1
    fi
    exec $2
    ;;
  list)
    list
    ;;
  *)
    show_help
    exit 1
esac

exit 0