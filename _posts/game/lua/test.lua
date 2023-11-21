function OnEvent(event, arg)
    OutputLogMessage("event = %s, arg = %s\n", event, arg)
     if(event == 'MOUSE_BUTTON_RELEASED' and arg==5 ) then
                x, y = GetMousePosition();
                OutputLogMessage("Mouse is at %d, %d\n", x, y);
                AbortMacro();
                PressMouseButton(1)
                Sleep(100)
                ReleaseMouseButton(1)
     end
    if(event == 'MOUSE_BUTTON_RELEASED' and arg==4 ) then
           longMianGaoYuanMove()
    end
end

--[[
    ±ù·âº®Ô¨-ÁúÃß¸ßÔ­
]]
function longMianGaoYuanMove()
	move(49894, 13058)
    move(46479, 18768)
    move(46411, 20225)
    move(46411, 20225)
    move(46957, 19193)
    move(52319, 32798)
    move(51977, 19679)
    move(52011, 19679)
    move(52319, 23687)
    move(51602, 16885)
    move(43679, 12269)
    move(43405, 12451)
    move(41288, 12633)
    move(42859, 14273)
    move(45386, 17492)
    move(49621, 26542)
    move(50748, 37960)
    move(49382, 44216)
    move(47128, 48164)
    move(46820, 47253)
    move(46342, 45613)
    move(50850, 45856)
    move(43201, 51019)
    move(52387, 40876)
    move(54573, 33162)
    move(49587, 17614)
    move(43713, 16946)
    move(42927, 17553)
   -- Sleep(2000)
   -- move(53821, 42819)
end

--[[
ÒÆ¶¯
]]
function move(x,y)
    attack()
    Sleep(200)
    MoveMouseTo(x,y)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(100)
    PressKey('a')
    Sleep(100)
    ReleaseKey('a')

    Sleep(500)
end

--[[
  ¹¥»÷
]]
function attack()
  moling()
end

--[[
    Ä§Áé
]]
function moling()
	PressKey('e')
    Sleep(100)
    ReleaseKey('e')
end