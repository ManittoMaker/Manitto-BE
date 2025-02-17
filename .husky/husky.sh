#!/bin/sh
if [ -z "$husky_skip_init" ]; then
  readonly hook_name="$(basename -- "$0")"
  echo "시작된 훅 이름 : $hook_name"

  readonly husky_skip_init=1
  export husky_skip_init

  sh -e "$0" "$@"
  exitCode="$?"

  if [ $exitCode != 0 ]; then
    echo "husky(error) - 훅 오류 발생, 이름:{$hook_name} 에러코드:{$exitCode}"
  fi

  exit $exitCode
fi
