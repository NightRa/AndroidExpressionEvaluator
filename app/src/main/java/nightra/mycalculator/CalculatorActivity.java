package nightra.mycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import fj.F;
import fj.Unit;
import fj.data.List;
import nightra.mycalculator.states.EmptyState;
import nightra.mycalculator.states.State;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static fj.Unit.unit;
import static nightra.mycalculator.states.Transitions.*;

public class CalculatorActivity extends Activity {

  TextView expressionText;
  Button button0;
  Button button1;
  Button button2;
  Button button3;
  Button button4;
  Button button5;
  Button button6;
  Button button7;
  Button button8;
  Button button9;
  Button dotButton;
  Button leftParenButton;
  Button rightParenButton;
  Button clearButton;
  Button evalButton;
  Button plusButton;
  Button minusButton;
  Button multiplyButton;
  Button divisionButton;
  Button sqrtButton;
  Button sinButton;
  Button cosButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calculator);

    expressionText = (TextView) findViewById(R.id.expressionText);
    button0 = (Button) findViewById(R.id.button0);
    button1 = (Button) findViewById(R.id.button1);
    button2 = (Button) findViewById(R.id.button2);
    button3 = (Button) findViewById(R.id.button3);
    button4 = (Button) findViewById(R.id.button4);
    button5 = (Button) findViewById(R.id.button5);
    button6 = (Button) findViewById(R.id.button6);
    button7 = (Button) findViewById(R.id.button7);
    button8 = (Button) findViewById(R.id.button8);
    button9 = (Button) findViewById(R.id.button9);
    dotButton = (Button) findViewById(R.id.buttonDot);
    leftParenButton = (Button) findViewById(R.id.leftParenButton);
    rightParenButton = (Button) findViewById(R.id.rightParenButton);
    clearButton = (Button) findViewById(R.id.clearButton);
    evalButton = (Button) findViewById(R.id.buttonEval);
    plusButton = (Button) findViewById(R.id.buttonPlus);
    minusButton = (Button) findViewById(R.id.buttonMinus);
    multiplyButton = (Button) findViewById(R.id.buttonMultiply);
    divisionButton = (Button) findViewById(R.id.buttonDiv);
    sqrtButton = (Button) findViewById(R.id.sqrtButton);
    sinButton = (Button) findViewById(R.id.sinButton);
    cosButton = (Button) findViewById(R.id.cosButton);

    Observable<F<State, State>> b0Clicks = clicksConst(button0, handleDigit(0));
    Observable<F<State, State>> b1Clicks = clicksConst(button1, handleDigit(1));
    Observable<F<State, State>> b2Clicks = clicksConst(button2, handleDigit(2));
    Observable<F<State, State>> b3Clicks = clicksConst(button3, handleDigit(3));
    Observable<F<State, State>> b4Clicks = clicksConst(button4, handleDigit(4));
    Observable<F<State, State>> b5Clicks = clicksConst(button5, handleDigit(5));
    Observable<F<State, State>> b6Clicks = clicksConst(button6, handleDigit(6));
    Observable<F<State, State>> b7Clicks = clicksConst(button7, handleDigit(7));
    Observable<F<State, State>> b8Clicks = clicksConst(button8, handleDigit(8));
    Observable<F<State, State>> b9Clicks = clicksConst(button9, handleDigit(9));
    Observable<F<State, State>> dotClicks = clicksConst(dotButton, handleDot);
    Observable<F<State, State>> leftParenClicks = clicksConst(leftParenButton, handleLeftBrace);
    Observable<F<State, State>> rightParenClicks = clicksConst(rightParenButton, handleRightBrace);
    Observable<F<State, State>> clearClicks = clicksConst(clearButton, handleClear);
    Observable<F<State, State>> evalClicks = clicksConst(evalButton, handleEval);
    Observable<F<State, State>> plusClicks = clicksConst(plusButton, handlePlus);
    Observable<F<State, State>> minusClicks = clicksConst(minusButton, handleMinus);
    Observable<F<State, State>> multiplyClicks = clicksConst(multiplyButton, handleMultiply);
    Observable<F<State, State>> divClicks = clicksConst(divisionButton, handleDivision);
    Observable<F<State, State>> sqrtClicks = clicksConst(sqrtButton, handleSqrt);
    Observable<F<State, State>> sinClicks = clicksConst(sinButton, handleSin);
    Observable<F<State, State>> cosClicks = clicksConst(cosButton, handleCos);

    Observable<F<State, State>> mergedClicks = Observable.merge(List.list(
            b0Clicks, b1Clicks, b2Clicks, b3Clicks, b4Clicks, b5Clicks, b6Clicks, b7Clicks, b8Clicks, b9Clicks,
            dotClicks, leftParenClicks, rightParenClicks, clearClicks, evalClicks,
            plusClicks, minusClicks, multiplyClicks, divClicks, sqrtClicks, sinClicks, cosClicks
    ));
    Observable<State> summingClicks = mergedClicks.scan(new EmptyState(), (s, t) -> t.f(s));
    Observable<String> sumsString = summingClicks.map(state ->
            state.toString(getString(R.string.onEmptyState),
                    err -> err.cata(getString(R.string.onSyntaxError), getString(R.string.onMathError))));
    sumsString.subscribeOn(AndroidSchedulers.mainThread()).subscribe(s -> expressionText.setText(s));

    expressionText.setText(getString(R.string.onEmptyState));
  }

  public static Observable<Unit> clicks(View view) {
    return Observable.<Unit>create((Subscriber<? super Unit> subscriber) -> view.setOnClickListener(v -> subscriber.onNext(unit())));
  }

  public static <A> Observable<A> clicksConst(View view, A value) {
    return clicks(view).map(u -> value);
  }
}
