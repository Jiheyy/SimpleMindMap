# SimpleMindMap
java interface


# 개요	
리눅스 시스템 상에서 사용자가 백업을 원하는 파일이나 디렉토리를 옵션에 따라 추가, 삭제하고 백업된 파일을 다시 복구하는 것을 관리하는 프로그램이다. 프로그램에 사용되는 명령어는 add, remove, compare, recover, list, exit, ls, vim 가 있으며 add 명령어의 옵션은 –m, -n, -t, -d가 있으며 각 명령어는 NUMBER ,TIME, DIR 등 추가적으로 입력이 되어야 하며, 각 옵션을 수행하기 필요한 요소가 입력되지 않았을 경우 에러를 출력하는 식으로 예외 처리를 한다. Remove 명령어는 -a 의 옵션을 둘 수 있다. recover의 경우는 -n의 옵션을 둘 수 있고, -n은 NEWFILE의 입력을 필요로 한다. 이 프로그램은 기본적으로 하나의 쓰레드가 한 개 파일의 백업을 담당하고 있다. 프로그램을 실행할 때, 사용자가 백업 디렉토리를 설정 가능하고, 설정하지 않았을 경우, 현재 있는 디렉토리에 backup이라는 이름의 백업 디렉토리를 생성해 백업파일, log파일을 저장한다. log파일은 add, remove, recover 명령어가 수행 됬을 경우, 수행시간과 수행내역을 로그파일에 기록한다. 로그파일은 실행 순서대로 순차적으로 기록이 되도록 한다. 이 이 프로젝트를 통해 새로운 명령어를 시스템 함수를 사용하여 구현함으로써 쉘의 원리를 이해하고, 유닉스/리눅스 시스템에서 제공하는 여러 시스템 자료구조를 이용하여 프로그램을 작성함으로써 시스템 프로그래밍 설계 및 응용 능력을 향상하는 것을 목표로 한다.

# 실행결과
![1](https://user-images.githubusercontent.com/54221681/138834745-2954f3a2-17ad-4081-bc70-878f5b942c54.PNG)
## add 명령어
![2](https://user-images.githubusercontent.com/54221681/138834760-13ea5c1c-44d4-4c45-906c-d36e9c879c23.PNG)

사용자가 백업 디렉토리를 설정하지 않고 실행했을 경우 backup 디렉토리가 현재 디렉토리 내에 생성되어 백업 디렉토리로 사용된다.

## list. remove 명령어
![3](https://user-images.githubusercontent.com/54221681/138834766-3c5ec55d-a168-4147-ad6d-b2a1da2caea6.PNG)

List 명령어 : 백업작업에 수행중인 파일의 절대경로 , period, d옵션이 출력된다.

Remove 명령어 : 모든 리스트가 제거된 후에도 새로 추가 가능하다. 

![4](https://user-images.githubusercontent.com/54221681/138834769-80cb5d82-1d67-46ec-9a68-1d31d55a0f99.PNG)

Recover 명령어 –n 옵션을 추가해 new 파일을 생성하여 파일 1 의 내용을 복원한다. 복원된 파일의 내용을 출력한다.

![5](https://user-images.githubusercontent.com/54221681/138834771-4e936416-a606-4fe5-993e-da7325162294.PNG)


Vi 와 ls 명령어가 수행이 되는 것을 볼 수 있다.

## 수행전
![6](https://user-images.githubusercontent.com/54221681/138834776-eb7fac04-45a0-42f7-be68-8fa7691266e6.PNG)


## 수행후
![7](https://user-images.githubusercontent.com/54221681/138834781-403757d8-cabb-4470-936d-71d380cdcd26.PNG)

수행 후 Add –n 옵션 새로 생긴 3개의 파일들만 남긴다.

![8](https://user-images.githubusercontent.com/54221681/138834785-a7a06021-5d62-4d4f-9472-9053b352dafb.PNG)

Log 파일에 기록이 남는다.

![9](https://user-images.githubusercontent.com/54221681/138834800-ce20445b-812a-4369-8b91-bcc4443bc989.PNG)

Compare 백업할 파일 끼리 비교, 백업된 파일 비교, 같은 백업파일 비교


