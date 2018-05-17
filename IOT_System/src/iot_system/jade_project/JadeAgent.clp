(deftemplate TS      (declare (from-class TemperatureSensor)))
(deftemplate S   (declare (from-class Sensor)))
(deftemplate R(declare (from-class Room)))
(deftemplate P   (declare (from-class Person)))

(defrule proposal
 "When a 'cfp' message arrives from an agent ?s, this rule asserts a 
  'propose' message to the same sender and retract the just arrived message"
 ?m <- (ACLMessage (communicative-act CFP) (sender ?s) (content ?c) (receiver ?r))
 =>
; (send (assert (ACLMessage (communicative-act PROPOSE) (receiver ?s) (content ?c) )))
 (assert (ACLMessage (communicative-act PROPOSE) (sender ?r) (receiver ?s) (content ?c) ))
 (retract ?m)
)

(defrule send-a-message
 "When a message is asserted whose sender is this agent, the message is
  sent and then retracted from the knowledge base."
 (MyAgent (name ?n))
 ?m <- (ACLMessage (sender ?n))
 =>
 (send ?m)
 (retract ?m)
)

(deffunction adjustRoomTemperature (?currRoomTempDeg ?outsideTemperatureDeg)
(if (< ?outsideTemperatureDeg 15) then (return (> ?currRoomTempDeg ?outsideTemperatureDeg))
else (return ?b)
)
)

(watch facts)
(watch all)
(reset) 

(run)  
; if you put run here, Jess is run before waiting for a message arrival,
; if you do not put (run here, the agent waits before for the arrival of the 
; first message and then runs Jess.








