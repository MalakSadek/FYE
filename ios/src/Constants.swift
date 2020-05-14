//
//  Constants.swift
//  FYEApp
//
//  Created by Malak Sadek on 7/19/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import Firebase

struct Constants
{
    struct refs
    {
        static let databaseRoot = Database.database().reference()
        static let databaseChats = databaseRoot.child("chats")
    }
}

