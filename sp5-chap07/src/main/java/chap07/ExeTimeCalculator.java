package chap07;

public class ExeTimeCalculator implements Calculator{    // 공통 기능을 구현하는 객체 = 프록시

    private Calculator delegate;    //실제 핵심 기능을 실행하는 객체 = 대상 객체

    public ExeTimeCalculator(Calculator delegate){
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();

        System.out.printf("%s.factorial(%d) 실행 시간 = %d\n", delegate.getClass().getSimpleName(), num, (end - start));
        return result;
    }
}
