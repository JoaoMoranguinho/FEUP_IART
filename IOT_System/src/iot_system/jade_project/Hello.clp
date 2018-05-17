(import nrc.fuzzy.*)
;; Two globals to hold our FuzzyVariables for air temperature and fan speed
(defglobal ?*airTempFvar* = (new FuzzyVariable "airTemperature" 0.0 100.0 "Deg C"))
(defglobal ?*fanSpeedFvar* = (new FuzzyVariable "fanSpeed" 0.0 1000.0 "RPM"))
(defrule init "An initialization rule that adds the terms to the FuzzyVariables"
 =>
 ;; the nrc FuzzyJess functions are loaded
 (load-package nrc.fuzzy.jess.FuzzyFunctions)
 (bind ?rlf (new RightLinearFunction)) (bind ?llf (new LeftLinearFunction))
 ;; terms for the air temperature Fuzzy Variable and fan speed Fuzzy Variable
 (?*airTempFvar* addTerm "cold" (new RFuzzySet 0.0 20.0 ?rlf))
 (?*airTempFvar* addTerm "OK" (new TriangleFuzzySet 0.0 20.0 35.0))
 (?*airTempFvar* addTerm "hot" (new LFuzzySet 20.0 35.0 ?llf))
 (?*fanSpeedFvar* addTerm "low" (new RFuzzySet 0.0 500.0 ?rlf))
 (?*fanSpeedFvar* addTerm "medium" (new TriangleFuzzySet 250.0 500.0 750.0))
 (?*fanSpeedFvar* addTerm "high" (new LFuzzySet 500.0 1000.0 ?llf))
 ;; assert the first the fuzzy input -- temperature at 0.0
 (assert (crispAirTemp 0.0))
 (assert (airTemp (new FuzzyValue ?*airTempFvar* (new TriangleFuzzySet 0.0 0.0 0.0)))))
(defrule temp-cold-fanSpeed-low "if air temperature cold then set fan speed low"
 (airTemp ?t&:(fuzzy-match ?t "cold"))
 =>
 (assert (fanSpeed (new FuzzyValue ?*fanSpeedFvar* "low"))))
(defrule temp-OK-fanSpeed-medium "if air temperature OK then set fan speed medium"
 (airTemp ?t&:(fuzzy-match ?t "OK"))
 =>
 (assert (fanSpeed (new FuzzyValue ?*fanSpeedFvar* "medium"))))
(defrule temp-hot-fanSpeed-high "if air temperature hot then set fan speed high"
 (airTemp ?t&:(fuzzy-match ?t "hot"))
 =>
 (assert (fanSpeed (new FuzzyValue ?*fanSpeedFvar* "high"))))
(defrule control "printing of results and initiating next iteration"
 ;; to combine output of all 3 rules we must wait until the 3 rules
 ;; have all fired ... low salience for this rule achieves this
 (declare (salience -100))
 ?catf <- (crispAirTemp ?t)
 ?fsf <- (fanSpeed ?fuzzyFanSpeed)
 =>
 ;; defuzzify the fan speed fuzzy value and print out the result
 (bind ?crispFanSpeed (?fuzzyFanSpeed momentDefuzzify)) (bind ?t (+ ?t 2.0))
 (printout t "For temp = " ?t " Fan speed set to "?crispFanSpeed " RPM" crlf)
 (if (<= ?t 50.0) then
 (retract ?catf ?fsf) (assert (crispAirTemp ?t))
 (assert (airTemp (new FuzzyValue ?*airTempFvar* (new TriangleFuzzySet ?t ?t ?t))))))