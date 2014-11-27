package scau.info.volunteertime.activity.activitycenter;


import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

//报名的旋转特效
public class Mycamera extends Animation {
	int centerX, centerY;
	boolean isZoomin;
	Camera camera = new Camera();
	public Mycamera(boolean isZoomin) {
		this.isZoomin = isZoomin;
	}
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		
		centerX = width / 2;
		centerY = height / 2;
		setDuration(200); 
		setFillAfter(true); 
		setInterpolator(new DecelerateInterpolator()); 
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final Matrix matrix = t.getMatrix();
		int direction = -1;
		camera.save(); 
		if (!isZoomin) {
			interpolatedTime = 1 - interpolatedTime;
			direction = 1;
		}
		
		camera.translate(0, 0, 200 + 200.0f * (interpolatedTime-1)); //x,y,z���ƫ����
		camera.rotateY(90 * interpolatedTime * direction); //��������Y����ת
		camera.getMatrix(matrix);//�����ǵ�����ͷ���ڱ任������
		matrix.preTranslate(-centerX, -centerY); 
		matrix.postTranslate(centerX, centerY);
		camera.restore(); 
	}

}
