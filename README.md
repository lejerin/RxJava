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
