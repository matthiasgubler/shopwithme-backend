db = db.getSiblingDB('swm')
db.dropDatabase();

//##### Persons

var max_id = "5e9c6d85d5375d17ce33bd32";
db.person.insert({
  _id: ObjectId(max_id),
  username: "maxmuster",
  email: "max@muster.ch",
  status: "ACTIVE",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Person"
});

var franziska_id = "5eaf154c32107b713fe0e2d7";
db.person.insert({
  _id: ObjectId(franziska_id),
  username: "fränzi",
  email: "franziska@me.com",
  status: "ACTIVE",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Person"
});

var liselotte_id = "5eaf1991d26a08a2b5d9ff1d";
db.person.insert({
  _id: ObjectId(liselotte_id),
  username: "liselotte",
  email: "liselotte@me.com",
  status: "ACTIVE",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Person"
});

var nofriends_id = "5eaf187cd7d8f5312719a638";
db.person.insert({
  _id: ObjectId(nofriends_id),
  username: "nofriends",
  email: "nofriends@me.com",
  status: "ACTIVE",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Person"
});

var lame_id = "5eaf1505a12a9d5b3fc466a1";
db.person.insert({
  _id: ObjectId(lame_id),
  username: "lame",
  email: "lame@me.com",
  status: "ACTIVE",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Person"
});

var unavailable_id = "5ea731a9fd5a645deea615df";
db.person.insert({
  _id: ObjectId(unavailable_id),
  username: "unavailable",
  email: "fred.unavailable@me.com",
  status: "ARCHIVED",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Person"
});

//##### Topics

//John
var john_topic1_id = "5eaf1481d01ac33f72541208";
db.topic.insert({
  _id: ObjectId(john_topic1_id),
  personId: max_id,
  title: "Lässiger Topic",
  description: "Bewertet doch meine tollen Schuhe",
  createDateTime: ISODate(),
  status: "ACTIVE",
  _class: "ch.zhaw.swm.wall.model.topic.Topic"
});

var john_topic2_id = "5eaf15656712615cd2502227";
db.topic.insert({
  _id: ObjectId(john_topic2_id),
  personId: max_id,
  title: "Noch ein Topic",
  description: "Beschreibung",
  createDateTime: ISODate(),
  status: "ACTIVE",
  _class: "ch.zhaw.swm.wall.model.topic.Topic"
});
db.topic.insert({
  _id: ObjectId("5eaf157e12c7087c0e20085a"),
  personId: max_id,
  title: "Archivierter Topic",
  description: "- :( -",
  createDateTime: ISODate(),
  status: "ARCHIVED",
  _class: "ch.zhaw.swm.wall.model.topic.Topic"
});

// Franziska
var franziska_topic1_id = "5ea6e6226f3dc315cf22eae1";
db.topic.insert({
  _id: ObjectId(franziska_topic1_id),
  personId: franziska_id,
  title: "Franziskas Hüte",
  description: "Heute ist shopping angesagt (mit Hüten)",
  createDateTime: ISODate(),
  status: "ACTIVE",
  _class: "ch.zhaw.swm.wall.model.topic.Topic"
});

//###### Relationships

//John - Franziska - ACCEPTED
db.relationship.insert({
  _id: ObjectId("5eaf18fec164dd47a99ef334"),
  requestingPersonId: max_id,
  requestedPersonId: franziska_id,
  status: "ACCEPTED",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Relationship"
})
db.relationship.insert({
  _id: ObjectId("5eaf197d7e79f4cb45c51bcf"),
  requestingPersonId: franziska_id,
  requestedPersonId: max_id,
  status: "ACCEPTED",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Relationship"
})

//Liselotte - John - PENDING
db.relationship.insert({
  _id: ObjectId("5eaf19b7a8cc18d0534777eb"),
  requestingPersonId: liselotte_id,
  requestedPersonId: max_id,
  status: "PENDING",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Relationship"
})

//John - lame - PENDING
db.relationship.insert({
  _id: ObjectId("5eb0257e5297c97051016a33"),
  requestingPersonId: max_id,
  requestedPersonId: lame_id,
  status: "PENDING",
  createDateTime: ISODate(),
  _class: "ch.zhaw.swm.wall.model.person.Relationship"
})


//##### Posts

//##### Posts.Comments
db.post.insert({
  _id: ObjectId("5eaf1c17fde64f01b9d09ef3"),
  topicId: franziska_topic1_id,
  personId: max_id,
  postType: "COMMENT",
  status: "ACTIVE",
  message: "Super tolli Hüet",
  createDateTime: ISODate(),
  status: "ACTIVE",
  _class: "ch.zhaw.swm.wall.model.post.Comment"
});
db.post.insert({
  _id: ObjectId("5eaf1d3af545418e58cb955f"),
  topicId: john_topic1_id,
  personId: max_id,
  postType: "COMMENT",
  status: "ACTIVE",
  message: "Ich kommentier mich halt selber, wenni susch kei Kollege han",
  createDateTime: ISODate(),
  status: "ACTIVE",
  _class: "ch.zhaw.swm.wall.model.post.Comment"
});

//##### Posts.Locations
db.post.insert({
  _id: ObjectId("5eaf1deffde64f01b9d09ef4"),
  topicId: john_topic1_id,
  personId: max_id,
  postType: "LOCATION",
  status: "ACTIVE",
  location: [47.497258, 8.7285392],
  createDateTime: ISODate(),
  status: "ACTIVE",
  _class: "ch.zhaw.swm.wall.model.post.Location"
});

//##### Posts.Images
// NOT YET IMPLEMENTED
