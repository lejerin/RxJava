# RxJava
리액티브 안드로이드 프로그래밍  
타다스 수보니스, 에이콘 출판사  

## Chapter 01  
2020-10-10  
버터나이프 대신 데이터바인딩 사용  
Recyclerview Adapter  
람다  

## Chapter 02  
2020-10-28  
옵저버블이란 무엇인가?  
옵저버블: 전송된 데이터에 관해서 관찰할 수 있는 데이터의 출처  
옵저버블이 생성됐을 때 구독한다고 하기 전까지는 데이터를 전송하지 않는다(콜드 옵저버블) -> .subscribe()호출 필요 
핫 옵저버블: 옵저버블이 만들어지면서 내부적으로 아이템을 생성(전송)한다, 구독자가 없으면 데이터 손실  

디스포저블이란 무엇인가?  
옵저버블의 생애 주기를 제어하기 위해 사용하는 도구  
옵저버블이 생성하는 데이터의 스트림이 무한이라면 메모리 누수의 원인이 됨  
CompositeDisposable을 통해 그룹화할 수 있다.  

스케줄러?  
현재 또는 나중에 동작해야 할 단위의 일정을 만드는 것, 스레드 선택 가능  
Schedulers.io(), Schedulers.computation(), Schedulers.newThread(), AndroidSchedulers.mainThread()  
안드로이드에서는 모든 ui변경이 메인 스레이드에서 동작해야함  
백그라운드에서 동작하는 프로세스의 결과를 메인스레드에 보여줘야 하는 경우 .observeOn()을 사용  

플로어블?  
옵저버블과 비슷한 메소드의 특징을 갖고있지만 더 빨리 방출된 아이템들을 처리할 수 있다.  
ex) 1백만 개의 아이템을 1초에 방출하는 출처가 있을 때 outOfMemory 예외 발생 -> 플로어블 사용
observable.toFlowable(BackpressureStrategy.MISSING)  
          .observeOn(Schedulers.computation))  
          .subscribe(v -> log("s", v.toString()), this::log);  
backpressure 전략:  
1. 아이템 버리기  
옵저버블의 속도를 따라잡지 못할 때, 처리하지 못하는 아이템을 그냥 버리는 것  
ex) drop, missing, simple  
2. 마지막 아이템 유지하기  
처리가능할 때까지 값을 방출하는 것을 멈춘다는 의미  
멈춰있는 동안 넘어오는 모든 값을 버리면서 나머지 값만 남겨두고, 처리할 수 있는 상황이 되면 마지막으로 저장된 메세지를 전송함  
ex) latest, missing-onbackpressurelatest, debounce  
3. 버퍼링  
앞으로 생길 문제를 미루는 격, 컨슈머가 따라잡지 못하면 일정 시점에서 버퍼는 고갈되고 메모리 고갈과 비슷한 증상 발생  
ex) buffer, missing.onbackpressureBuffer, buffer  

옵저버블, 플로어블 이외의 3가지 타입  
1. 컴플리터블: 미래에 완료될 결과가 없는 행위를 나타냄  
onComplete or onError 두가지 타입만 처리  
2. 싱글: 스트림 대신 하나의 아이템을 반환  
3. 메이비: 어떠한 값의 반환 없이 완료,실패 할 수 있는 행위를 의미, 아이템반환x  


## Chapter 03  
2020-10-29  
yahoo 대신 aws 서버 api  
retrofit2  

## Chapter 05  
2020-10-31  
스케줄러란?  
싱글, 트램펄린, 뉴스레드, IO, 컴퓨테이션, 이그제큐터, 안드로이드 유형이 있음  
싱글: Schedulers.single() 단일 스레드에 의해 뒷받침되므로 코드가 항상 해당 스레드에서만 실행됨  
트램펄린: 현재 스레드에서 코드를 실행하는 스케줄러, 현재 스레드의 큐에 실행될 코드 블록을 추가함, 꼭 필요한 곳이 있다고 생각되면 Scheduler.trampoline()을 호출해 만들 수 있다.  
뉴스레드: 각 옵저버블이 활성 상태가 되면 새 스레드를 만든다. 단, 옵저버블마다 새로운 스레드를 생성하는 것은 무거운 연산: 1000개 옵저버블,1000개 스레드  
IO: 가장 많이 사용하는 스케줄러, 네트워크 요청, 파일시스템 접근, 안드로이드의 콘텐트 리졸버와 같은 IO 관련 작업 부하에 적합, 워커풀/ 풀은 한 워커로 시작하고 사용 가능할 때 다른 옵저버 간에 재사용 된다. 사용할 수 있는 워커가 없으면 새 워커가 생성된다. 풀의 크기에는 제한이 없고, 사용하지 않는 워커는 60초 후에 자동 제거된다.   
컴퓨테이션: 워커 풀에 의해 지원된다는 점에서 IO와 비슷하지만 풀 크기가 무제한이 아닌 시스템 사용 코어 수로 고정된다. 워커 수 가 부족하면 기다려야한다. 그러나 빠르게 계산 되고 메인 스레드에서 완료해야할 때 적합하다. EX) 이벤트 루프, 버스 , UI이벤트 처리, 복잡한 수학 계산  
이그제큐터 스케줄러: Executors 클래스로 생성된 커스텀 풀을 사용하기 위해 만들어졌다. 옵저버블을 많이 만들 가능성이 있는 경우 io 스케줄러를 대체할 수 있는 제한된 워커 풀을 만드는데 사용할 수 있다.  
안드로이드 스케줄러: 코드의 실행을 안드로이드의 메인 스레드로 되돌려보내기 위해 사용됨. ui 수정을 위해  

스케줄러 사용:  
.subscribeOn() -> 원천 옵저버블이 아이템을 방출하기로 돼 있는 스케줄러를 수정한다. (모든 데이터 처리가 시작되는 스레드를 수정) 즉, 여러번 호출해도 첫번째것만 유효함  
.observeOn() -> 호출될 때마다 코드가 실행되는 아래쪽 스레드가 변경된다.  

두가지 사용 법칙  
1. 옵저버블 흐름에 가능한 앞쪽에 .subscribeOn()을 배치하자!  
2. .subscribeOn() 바로 앞에 observeOn()을 배치하자!  

코드는 동기적으로 실행되지만 코드가 다른 스레드에서 실행된다면 정의된 순서에 따라 실행된다.  
StorIO 대신 Room 사용  

## Chapter 06  
2020-11-01  
RxJava 예외 처리 방법  
.subscrive() 메소드는 옵저버블 또는 연산자가 던진 예외를 처리하는 데 이용하는 추가 인수를 사용한다  
Observable.just("one")
          .doOnNext(i -> {
                    throw new RuntimeException();
           })
           .subscribe(item -> {
                    log("subscribe", item);
            }, throwable -> {
                    log(throwable);
            });  
이와 같이 두 번째 인수를 사용하여 예외 처리  

onExceptionResumeNext()를 사용한 오류 처리 방법  
onExceptionResumeNext(): 예외 발생 후 다른 옵저버블에서 흐름 처리를 복원하는 좋은 방법, 원본이 실패한 경우 백업 옵저버블에 플러그인하는 매커니즘으로 사용 가능  
Observable.<String>(new RuntimeException("Crash!"))
          .onExceptionResumeNext(Observable.just("second"))
          .subscribe(item -> {
                    log("subscribe", item);
          }, e -> log("subscribe",e));  
  
doOnError()를 사용한 오류 처리 방법 : .subscribe() 에 아직 도달하지 않은 오류를 가로채기 위해 사용한다.  
Observable.<String>(new RuntimeException("Crash!"))
          .doOnError(e -> log("doOnNext",e))
          .onExceptionResumeNext(Observable.just("second"))
          .subscribe(item -> {
                    log("subscribe", item);
          }, e -> log("subscribe",e));  
  
그 외 기타 오휴 처리 방법: onErrorResumeNext(), onErrorReturn(), onErrorEeturnItem()  

예외 로깅 중앙 집중화 방법  
중앙 처리기: 일반적인 예외 처리가 필요한 장소에서 재사용되는 핸들러를 사용하는 것  
RxJava 플러그인 사용: RxJavaPlugins.setErrorHandler(...);  
db에서 데이터 읽어오기  


