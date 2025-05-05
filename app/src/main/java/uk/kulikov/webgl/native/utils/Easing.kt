package uk.kulikov.webgl.native.utils

data class Vector3(
    val x: VectorPosition,
    val y: VectorPosition,
    val z: VectorPosition
)

data class VectorPosition(
    var current: Double,
    var velocity: Double = 0.0
)

fun damp(
    vectorPosition: VectorPosition,
    /** To goal value */
    targetInput: Double,
    /** Approximate time to reach the target. A smaller value will reach the target faster. */
    smoothTimeInput: Double = 0.25,
    /** Frame delta, for refreshrate independence */
    delta: Double = 0.01,
    /** Optionally allows you to clamp the maximum speed. If smoothTime is 0.25s and looks OK
     *  going between two close points but not for points far apart as it'll move very rapid,
     *  then a maxSpeed of e.g. 1 which will clamp the speed to 1 unit per second, it may now
     *  take much longer than smoothTime to reach the target if it is far away. */
    maxSpeed: Long = Long.MAX_VALUE,
    /** Easing function */
    easing: (Double) -> Double,
    /** End of animation precision */
    eps: Double = 0.001
): Boolean {
    var newTarget = targetInput
    if (Math.abs(vectorPosition.current - newTarget) <= eps) {
        vectorPosition.current = newTarget;
        return false;
    }

    val smoothTime = Math.max(0.0001, smoothTimeInput);
    val omega = 2 / smoothTime;
    val t = easing(omega * delta);
    var change = vectorPosition.current - newTarget;
    val originalTo = newTarget;
    // Clamp maximum maxSpeed
    val maxChange = maxSpeed * smoothTime;
    change = Math.min(Math.max(change, -maxChange), maxChange);
    newTarget = vectorPosition.current - change;
    val temp = (vectorPosition.velocity + omega * change) * delta;
    vectorPosition.velocity = (vectorPosition.velocity - omega * temp) * t;
    var output = newTarget + (change + temp) * t;
    // Prevent overshooting
    if (originalTo - vectorPosition.current > 0.0 == output > originalTo) {
        output = originalTo;
        vectorPosition.velocity = (output - originalTo) / delta;
    }
    vectorPosition.current = output;
    return true;
}

data class Vector3Target(
    val x: Double,
    val y: Double,
    val z: Double
)

fun damp3(
    current: Vector3,
    target: Vector3Target,
    smoothTime: Double = 0.25,
    delta: Double = 0.01,
    maxSpeed: Long = Long.MAX_VALUE,
    easing: (Double) -> Double,
    eps: Double = 0.001
): Boolean {
    val a3 = damp(current.x, target.x, smoothTime, delta, maxSpeed, easing, eps);
    val b3 = damp(current.y, target.y, smoothTime, delta, maxSpeed, easing, eps);
    val c3 = damp(current.z, target.z, smoothTime, delta, maxSpeed, easing, eps);
    return a3 || b3 || c3;
}